Signup - Ldap based self-service app for account creation
===========================================================
Small "hack" written for http://thw-koeln.de


Features
--------------
* Users are able to register (via selfservice) and to reset their
passwords. 
* Admins can confirm or reject registered accounts.


Building, etc.
----------------
The application is written in Java (using Google Web Toolkit) and build via
GWT (no ant / maven / ivy).
The Account-workflow is tracked in Apache derby, all confirmed accounts are written to
ldap.

Misc
----------------
Please contact me if you've any questions.
