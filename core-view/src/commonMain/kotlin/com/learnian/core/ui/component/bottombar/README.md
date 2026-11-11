# ClayBottomBar & ClayBottomNavItem

Claymorphism bottom dock bar component implementing **Sunken Dock Navbar** specs with active **Action Raised** selection pill items.

## Features
- **Sunken Dock Base**: Inset grooved dug-in base container.
- **Action Raised Active Tab**: Floating raised active indicator item with spring click response.

## Code Example

```kotlin
ClayBottomBar {
    ClayBottomNavItem(
        selected = currentTab == Tab.Home,
        onClick = { currentTab = Tab.Home },
        icon = { Icon(Icons.Default.Home, contentDescription = null) },
        label = "خانه"
    )
    ClayBottomNavItem(
        selected = currentTab == Tab.Profile,
        onClick = { currentTab = Tab.Profile },
        icon = { Icon(Icons.Default.Person, contentDescription = null) },
        label = "پروفایل"
    )
}
```
