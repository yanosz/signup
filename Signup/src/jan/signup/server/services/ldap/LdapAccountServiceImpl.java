package jan.signup.server.services.ldap;

import jan.signup.server.AppProps;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

public class LdapAccountServiceImpl implements LdapAccountService{

	private final String ldapUrl;
	private final String basedn;
	private final String binddn;
	private final String bindpw;
	public final String incubatorDn;
	private final static String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private final static String SECURITY_AUTHENTICATION = "simple";
	private Logger log = Logger.getLogger(LdapAccountService.class);
	private AppProps props;
	
	public LdapAccountServiceImpl() {
		super();
		props = AppProps.getDefaultInstance();
		this.ldapUrl = props.getLdapUrl();
		this.basedn = props.getLdapBaseDN();
		this.binddn = props.getLdapBindDN();
		this.bindpw = props.getBindPw();
		this.incubatorDn = props.getLdapIncubatorDn();
	}

	@Override
	public void setPassword(LdapUser user){
		try {
			DirContext ctxt = connect();
			String hashedPassword = digestSha1(user.getPassword());
			Attribute pwd = new BasicAttribute("userpassword", hashedPassword);
			ModificationItem [] modItems = new ModificationItem[1]; 
			modItems[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, pwd);
			LdapName dn = new LdapName(user.getDn());
			ctxt.modifyAttributes(dn, modItems);
		} catch (NamingException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createUser(LdapUser user,String parentDn){
		try {
			String cn = buildCn(user.getFirstName(),user.getLastName());
			BasicAttributes attrs = getBasicAttributesForUser(user, cn);
			attrs.put("forward",user.getForwardAddress());
			DirContext ctxt = connect();
			System.err.println(attrs);
			ctxt.bind("cn="+cn+","+parentDn,null,attrs);
		} catch (NamingException e) {
			log.error("Error createing User",e);
			throw new RuntimeException(e);
		}
	}
	@Override
	public void createImapUser(LdapUser user, String parentDn) {
		String cn = buildCn(user.getFirstName(),user.getLastName());
		int uid = getMaxUid() + 1;
		BasicAttributes attrs = getBasicAttributesForUser(user, cn);
		attrs.get("objectclass").add("posixAccount");
		attrs.put("gidnumber",props.getLdapGidNumber());
		attrs.put(props.getLdapUidAttribute(),uid+"");
		attrs.put("homedirectory",props.getHomeDirBase()+"/"+cn);
		attrs.put("uid",cn);
		attrs.put("forward",cn+"@localhost");
		try {
			DirContext ctxt = connect();
			ctxt.bind("cn="+cn+","+parentDn,null,attrs);
		} catch (NamingException e) {
			log.error("Error creating user",e);
			throw new RuntimeException(e);
		}
	}
	
	
	private BasicAttributes getBasicAttributesForUser(LdapUser user, String cn) {
		BasicAttributes attrs = new BasicAttributes();
		BasicAttribute oclass = new BasicAttribute("objectclass");
		oclass.add("mailAccount");
		oclass.add("inetOrgPerson");
		oclass.add("person");
		oclass.add("top");
		user.setCn(cn);
		attrs.put(oclass);
		attrs.put("cn",cn);
		attrs.put("givenname",user.getFirstName());
		attrs.put("privateMail",user.getPrivateMailAdress());
		attrs.put("mail",user.getMailAdress());
		attrs.put("sn",user.getLastName());
		attrs.put("userpassword",digestSha1(user.getPassword()));
		return attrs;
	}

	

	private int getMaxUid(){
		int ret = -2;
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		sc.setReturningAttributes(new String [] {props.getLdapUidAttribute()});
		String filter = "objectclass=posixAccount";
		
		DirContext ctxt;
		try {
			ctxt = connect();
			NamingEnumeration<SearchResult> results = ctxt.search(basedn, filter, sc);
			while(results.hasMore()){
				Attributes attrs = results.next().getAttributes();
				int uidNumber = Integer.parseInt(attrs.get(props.getLdapUidAttribute()).get().toString());
				if(uidNumber > ret){
					ret = uidNumber;
				}
			}
		} catch (Exception e) {
			log.fatal(e);
			throw new RuntimeException(e);
		}
		return ret;
	}
	
	
	@Override
	public List<String> getValidParents() {
		LinkedList<String> ret = new LinkedList<String>();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		DirContext ctxt;
		try {
			ctxt = connect();
			String filter = props.getLdapFilterForValidParents();
			NamingEnumeration<SearchResult> results = ctxt.search(basedn, filter, sc);
			while(results.hasMore()){
				SearchResult result = results.next();
				String dn = result.getNameInNamespace();
				ret.add(dn);
			}
		} catch (NamingException e) {
			log.fatal(e);
			throw new RuntimeException(e);
		}
		return ret;
	}


	private DirContext connect() throws NamingException{
		   Hashtable<String,String> env = new Hashtable<String, String>();   
	       env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
		   env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
	       env.put(Context.PROVIDER_URL, ldapUrl);  // SET YOUR SERVER AND STARTING CONTEXT HERE
	       env.put(Context.SECURITY_PRINCIPAL, binddn);  // SET USER 
	       env.put(Context.SECURITY_CREDENTIALS, bindpw);  // SET PASSWORD HERE
	       return new InitialDirContext(env);
	       
	}
	
	private String digestSha1(String input) {
		String base64;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA");
			digest.update(input.getBytes());
			base64 = new BASE64Encoder().encode(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return "{SHA}" + base64;
	}

	@Override
	public LdapUser getByCN(String cn) {
		return looukupByFilter("cn="+cn);
	}
	
	@Override
	public LdapUser getByEmail(String mail){
		return looukupByFilter("(|(forward="+mail+")(mail="+mail+")(privateMail="+mail+"))");
	}

	private LdapUser looukupByFilter(String filter){
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		DirContext ctxt;
		try {
			ctxt = connect();
			NamingEnumeration<SearchResult> results = ctxt.search(basedn, filter, sc);
			if(results.hasMore()){
				SearchResult result = results.next();
				Attributes attrs = result.getAttributes();
				String firstName = attrs.get("givenName").get().toString();
				String lastName = attrs.get("sn").get().toString();
				String cn = attrs.get("cn").get().toString();
				String dn = result.getNameInNamespace();
				String forwardAddress = attrs.get("forward").get().toString();
				String mailAdress = attrs.get("mail").get().toString();
				Attribute addrAttr = attrs.get("privateMail");
				if(addrAttr == null){
					addrAttr = attrs.get("forward");
				}
				String privateMailAdress = addrAttr.get().toString();
				
				LdapUser user = new LdapUser(dn, firstName, lastName, forwardAddress, mailAdress, privateMailAdress, cn);
				return user;
			}
		} catch (NamingException e) {
			log.fatal(e);
			throw new RuntimeException(e);
		}
		return null;
		
	}
	public String buildCn(String firstName, String lastName) {
		String firstNameEscaped = ldapEscape(firstName);
		String lastNameEscaped = ldapEscape(lastName);
		return firstNameEscaped+"."+lastNameEscaped;
	}

	private static String ldapEscape(final String input){
		//1. Runde - Downcase
		String output = input.toLowerCase(Locale.GERMANY);
		//2.Runde - Umlaute entfernen.
		output = output.replaceAll("ä", "ae");output =output.replaceAll("Ä","Ae");
		output =output.replaceAll("ö", "oe");output =output.replaceAll("Ö","oe");
		output =output.replaceAll("ü", "ue");output =output.replaceAll("Ü","Ue");
		output =output.replaceAll("ss", "ss");
		//3. Nicht-Word-Chars durch _ ersetzen
		output =output.replaceAll("\\W", "_");
		return output;
	}


	
}
