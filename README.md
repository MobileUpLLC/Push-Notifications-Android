# Push Notifications Android
This repository shows how to use Huawei or Google services for Push-notification in different flavors.
# Preparations
You need to create accounts in Huawei and Google, enable services in developer console and get config files and keys.

**Huawei**

1. Create developer account on App Gallery Connect;
2. Create application, define application id (package);
3. Enable Push in App Gallery Connect for Project;
4. Provide SHA-256 of signing keys;
5. Download agconnect-services.json and put in `app` folder;
6. If you use Huawei device for tests - make sure, that Huawei Mobile Services updated to latest version.

**Google**

1. Create Google and Firebase accounts;
2. Create project in Firebase;
3. Add android application in Firebase. Provide SHA-1 of signing keys;
4. Download google-services.json and put in `app` folder;
5. Make sure, that you have Google Mobile Services installed and enabled on you device.

**Unsplash**
1. Create developer account on Unsplash;
2. Get Access-token from Unsplash and put in variable `api.unsplash.access_token` on `local.properties`.

# Launch and Priority
Choose one of flavor `universal` or `gms` and see how it works!
Gms assembly is suitable for distribution on Google Play (work only GMS). Universal assembly is suitable for distribution on App Gallery (work with GMS and HMS).

**Warning:** If the device has HMS and GMS, then GMS is used in priority.
