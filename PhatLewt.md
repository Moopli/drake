# Introduction #

I don't like arbitrary numbers. "Sword of +13". What does the +13 mean anyway? Linear increase? Exponential increase? Who measures these numbers anyway? Is there some committee that debates on the merits of integral numbers for sword strength? No? Didn't think so. Anyways, that oughta be enough complaining. There is a light at the end of the tunnel -- physics. People don't actually measure material strength using arbitrary "+13"s and stuff -- they use numbers like Young's modulus, and measure shear resistance, compressive resistance, density, and so on. Of course, the material isn't the only factor -- the forging process is most important. The sharpness of the blade, the center of gravity, the flexibility of the blade, all sorts of stuff depend mostly on the skill of the manufacturer.

# As For Simulation #

Generating loads of different weapons requires plentiful RNG. Luckily, random numbers are a renewable resource.

First comes the metal used -- we'd like to be able to use different metals with different material properties to forge stuff (or even not metal at all -- bamboo stave, anyone?). There are many ways we could generate the metal/alloy to use, but we obviously (or at least probably) want higher-quality loot from better enemies, so we need some sort of quality value. Hey, I know, let's use an RNG! So it's simple then -- we somehow RNG up the quality of our weapons, create weapons to spec, equip our monsters, leave some loot lying around, and go! So, for example, the RNG would give us a quality number of 1337; which just happens to correspond to a 40%iron 60%mithril alloy. According to basic science, most metals average their properties when alloyed (not true, but meh), and certain impurities can have a large effect on the result.

Then on to the forging process -- partly based on the quality of the metal itself, we can make some assumptions of the quality of the smith. For example, it takes quite a skilled industry to manufacture amorphous metal, meaning the workmanship is that much more likely to be masterful. Different kinds of loot would have different values that are affected by workmanship -- edge weapons have sharpness, all weapons have balance, etc.


# Everything's Better with Flavor #

Your epic mace of great density is nothing if you can't gloat to your friends. Therefore, we need to be able to convert quantitative properties of weapons and stuff into  text; so a sword with a very low contact area would "have a very sharp edge", a warhammer which is well-balanced would be "well-balanced", etc you get the idea. So for example, a good sword you might find in some loot could be the "Perfectly balanced Amorphite Claymore of Eternal Sharpness", or a "Rusty Goblinite Dirk of Tetanic Infection". So, adjective material object of adjective noun-ness.


