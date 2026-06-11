
This template runs on **Java 25**, **Gradle 9.2.1** + **[RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) 2.0.2** + **Forge 14.23.5.2847**.
   
## Beginner's Guide to Minecraft Modding with This Template

Hello! If you're new to Minecraft modding, this guide will help you get started with creating your own addon for the Nuclear Tech Mod Community Edition (NTMCE). Don't worry if this sounds complicated - we'll take it step by step.

### What is Modding?
Modding means changing or adding things to a game. In Minecraft, mods can add new items, blocks, or change how the game works. NTMCE is a popular mod that adds nuclear reactors, bombs, and other high-tech stuff to Minecraft 1.12.2. An addon is a small mod that works with NTMCE to add even more features.

### What Does This Template Do?
This template is like a starter kit for making your own NTMCE addon. It sets up all the files and tools you need, so you don't have to start from scratch. It includes:
- A way to download NTMCE automatically
- Tools to build and test your addon
- Support for advanced features like mixins (don't worry if you don't know what that means yet)

### Getting Started
1. **Get the Template**: Click the "Use this template" button on GitHub to create your own copy of this project. Then download it to your computer.

2. **Install Java**: You need Java 25. If you don't have it, download it from the official Java website.

3. **Open in IntelliJ IDEA**: This is a free program for writing code. Download it from jetbrains.com. Open the folder you downloaded, and IDEA should recognize it as a project.

4. **Set Up the Workspace**: Run the "setupDecompWorkspace" task in Gradle. This sets up the Minecraft workspace and automatically downloads NTMCE if needed.

5. **Set Up Your Mod**: Before coding, customize your mod in `gradle.properties`:
   - Change `mod_id` to your unique mod ID (like "myaddon" - must be unique to avoid conflicts)
   - Change `mod_name` to your mod's display name
   - Change `root_package` to your Java package (like "com.yourname.youraddon")
   
   Then, refresh the Gradle project in IDEA. This runs `injectTags` to generate a Tags class for constants, and `generateMixinJson` to create/update the mixins config file.

6. **Update Mixins if Needed**: If you changed the mod_id, update the "package" in `src/main/resources/mixins.modid.json` to match your new root_package + ".mixins".

7. **Write Your Code**: Look in the src/main/java folder. This is where you write your addon's code. Start by changing the mod info in mcmod.info and adding your own classes.

8. **Build Your Addon**: Run the "build" task in Gradle. This creates a .jar file of your addon.

9. **Test It**: Run "runClient" to start Minecraft with your addon loaded. You can test if it works!

### Common Tasks
Here are some useful Gradle tasks you might use:
- build: Makes your addon file
- runClient: Starts Minecraft with your addon
- runServer: Starts a Minecraft server with your addon
- clean: Removes old build files

### Tips for Beginners
- Start small. Add one simple item or block first.
- Look at the code in the template for examples.
- If something doesn't work, check the error messages.
- If you want to change your mod's properties like ID, name, or version, edit the gradle.properties file.
- Join modding communities like the Cleanroom/1.12 Coalition Discord, or the NTMCE Discord for help.

Have fun creating your own Minecraft mods! If you get stuck, the advanced instructions above have more details.

## Using Mixins (Advanced)

Mixins are a powerful way to change how Minecraft or other mods work without editing their code directly. They let you add new methods, change existing ones, or modify classes at runtime. This is advanced stuff - only use mixins if you're comfortable with Java and modding.

### What Are Mixins?
Mixins are like patches for code. You write special classes that "mix in" changes to other classes. For example, you can make a Minecraft class do something extra when a method is called.

### Setting Up Mixins
Mixins are already enabled in this template. If you want to disable them, change `use_mixins = false` in `gradle.properties`.

The mixin config file is at `src/main/resources/mixins.modid.json`. It tells Minecraft which mixins to load.

### Creating a Mixin
1. Create a package for your mixins, like `com.example.modid.mixins`.

2. Write a mixin class. It should extend or interface with the class you want to change. Use annotations like `@Mixin`, `@Inject`, `@Overwrite`.

Example:
```java
@Mixin(SomeMinecraftClass.class)
public class MyMixin {
    @Inject(method = "someMethod", at = @At("HEAD"))
    private void myInjection(CallbackInfo ci) {
        // Your code here
    }
}
```

3. Add your mixin class to the mixins array in `mixins.modid.json`.

### Tips for Mixins
- Read the Mixin wiki: https://github.com/SpongePowered/Mixin/wiki
- Check CleanroomMC docs: https://cleanroommc.com/wiki/forge-mod-development/mixin/preface
- Test carefully - mixins can break things if done wrong.
- Use the MinecraftDev plugin for IntelliJ to help write mixins.
