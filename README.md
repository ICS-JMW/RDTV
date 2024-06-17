# RDTV
CS Department TV system
An application that can be hosted on a raspberry pi and connected to a television in order to display information about upcoming events.
Has an accompanying website that allows submissions and management of submissions.
By default submissions are put on hold until they are accepted.
After they are accepted, they can be displayed.

---
# Setting up
1. Install java jdk
2. Download this repository (either through git clone or some other method)
3. Ideally load this repository with the Netbeans Spring plugin
6. Compile and run spring application with Maven's `spring-boot run` goal
---
# Bugs 
1. If there are no submissions to be approved, /adminPanel displays error.
2. The screensavers might not stop playing, still investigating error

---
# Issues
1. There is no log in to admin so anyone can approve posts.
2. Many websites do not redirect properly and instead show redirect:/{page}.
3. Is not set up to go on any port, so only works on localhost:8080 as of now. This means that effectively, only the device owner can upload things onto it.

