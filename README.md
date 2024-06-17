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
4. Install `xscreensaver` and start it in the background
6. Compile and run spring application with Maven's `spring-boot run` goal
7. `Alt-f4` the gui windows if you do not need it
8. For people to submit it, go on localhost:8080/
---
# Websites
1. (/) main page where information and rules about uploading files are displayed.
2. (/upload) location where media and information about media can be sent to be reviewed.
3. (/adminPanel) location where media is sent to be reviewed. Pressing Accept allows it to be displayed onto the screen. Pressing deny rejects it. No user validation is done, so anyone can use these methods.

--- 
# Bugs 
1. If there are no submissions to be approved, /adminPanel displays error.
2. The screensavers might not stop playing, still investigating error
3. If media.bf (in src/main/resources/storage) and data.json are misaligned, then some issues with adminpanel. This hasn't been encountered during testing but did occur when manually messed with, issues do happen.
---
# Issues
1. There is no log in to admin so anyone can approve posts.
2. Many websites do not redirect properly and instead show redirect:/{page}.
3. Is not set up to go on any port, so only works on localhost:8080 as of now. This means that effectively, only the device owner can upload things onto it.

