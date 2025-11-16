# Contributing to Quest Monitor

First off, thank you for considering contributing to Quest Monitor! It's people like you that make Quest Monitor such a great tool for the Meta Quest 3 community.

## Code of Conduct

This project and everyone participating in it is governed by our commitment to creating a welcoming and inclusive environment. By participating, you are expected to uphold this standard.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues to avoid duplicates. When creating a bug report, include as many details as possible:

- **Use a clear and descriptive title**
- **Describe the exact steps to reproduce the problem**
- **Provide specific examples**
- **Describe the behavior you observed** and what you expected
- **Include screenshots** if applicable
- **Provide device and Android version information**

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. When creating an enhancement suggestion:

- **Use a clear and descriptive title**
- **Provide a step-by-step description** of the suggested enhancement
- **Explain why this enhancement would be useful** to Quest Monitor users
- **Include mockups or examples** if applicable

### Pull Requests

- Fill in the required template
- Follow the Kotlin coding style
- Include comments in your code where necessary
- Update documentation as needed
- Test on Meta Quest 3 when possible

## Development Setup

1. **Prerequisites**
   - Android Studio Hedgehog (2023.1.1) or later
   - JDK 17
   - Android SDK with API 34
   - Meta Quest 3 (for testing)

2. **Clone and Setup**
   ```bash
   git clone https://github.com/[username]/quest-monitor-android.git
   cd quest-monitor-android
   ```

3. **Open in Android Studio**
   - File → Open → Select project directory
   - Sync Gradle files

4. **Run on Device**
   - Connect Meta Quest 3 via ADB
   - Run → Run 'app'

## Coding Conventions

### Kotlin Style Guide

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Prefer `val` over `var` when possible
- Use extension functions appropriately

### Project Structure

```
app/src/main/java/com/questmonitor/
├── models/          # Data models
├── repositories/    # Data layer
├── viewmodels/      # Business logic
├── ui/
│   ├── activities/  # Activities
│   ├── fragments/   # Fragments
│   ├── adapters/    # RecyclerView adapters
│   └── views/       # Custom views
└── utils/           # Utility classes
```

### Naming Conventions

- **Classes**: PascalCase (e.g., `DashboardViewModel`)
- **Functions**: camelCase (e.g., `getCpuInfo`)
- **Variables**: camelCase (e.g., `memoryUsage`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `REFRESH_INTERVAL`)
- **Resources**: snake_case (e.g., `fragment_dashboard.xml`)

### Comments

- Use KDoc for public APIs
- Explain **why**, not what (code should be self-explanatory)
- Keep comments up-to-date with code changes

Example:
```kotlin
/**
 * Retrieves current CPU usage percentage
 *
 * @return CPU usage as a float between 0 and 100
 */
fun getCpuUsage(): Float {
    // Implementation
}
```

## Git Commit Messages

- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit first line to 72 characters
- Reference issues and pull requests after the first line

Examples:
```
feat: add network speed testing feature

fix: resolve battery percentage calculation error

docs: update README with new screenshots

refactor: improve memory usage in dashboard
```

### Commit Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

## Testing Guidelines

- Test on Meta Quest 3 when possible
- Verify both light and dark themes
- Check all screen orientations
- Test with different refresh intervals
- Verify permission handling
- Test memory cleanup functionality

## Additional Notes

### Issue and Pull Request Labels

- `bug`: Something isn't working
- `enhancement`: New feature or request
- `documentation`: Documentation improvements
- `good first issue`: Good for newcomers
- `help wanted`: Extra attention needed
- `question`: Further information requested

## Recognition

Contributors will be recognized in the project README. Thank you for your contributions!

## Questions?

Feel free to open an issue with the `question` label or start a discussion in GitHub Discussions.

Thank you for contributing to Quest Monitor! 🚀
