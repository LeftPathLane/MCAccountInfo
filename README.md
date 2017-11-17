# MCAccountInfo
Implementation of the Mojang User Info API

## Installation
MCAccountInfo is distributed as a [maven](http://maven.apache.org) project. To compile it and install it in your local Maven repository Jars are found in `/target`:
```ShellSession
git clone https://github.com/LeftPathLane/MCAccountInfo.git
cd MCAccountInfo
mvn clean install
cd target
```

## Usage
```ShellSession
java -jar OGInfo.jar USERNAME PASSWORD
```
output explained [taken from Minecraft Modern wiki](http://wiki.vg/Mojang_API)
```json
{
    "id": "<Account Identifier?>",
    "email": "<Email attached to account, in legacy users this may be a hash>",
    "username": "<Username of account, with migrated accounts this is the same as email>",
    "registerIp": "<IP used to register account>",
    "registeredAt": <Epoch timestamp in ms of date the Mojang account was registered>,
    "passwordChangedAt": <Epoch timestamp of time password was last changed>,
    "dateOfBirth":  <Epoch timestamp of date of birth for this Mojang Account>,
    "deleted": <Unknown, probably whether account has been deleted or not>,
    "blocked": <Unknown, probably whether account has been blocked or not>,
    "secured": <Whether security questions are enabled on this Mojang Account>,
    "migrated": <Whether the account has been migrated, if the account was made after Mojang Accounts were mandatory for new accounts this is set to false>,
    "emailVerified": <Whether the email attached to the account is verified>,
    "legacyUser": <Whether the account is a legacy user>,
    "verifiedByParent": <Whether the account has been verified by parent, is set to false if no parent verification was needed>,
    "fullName": "<Full name attached to Mojang account, can be an empty string>",
    "fromMigratedUser": <Not sure, probably related to migrated?>,
    "hashed": <Legacy users' emails are hashed, this value is set to true in most legacyusers>
}
```