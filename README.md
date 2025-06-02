### 🧪 Running Tests with Cucumber Tags

You can use the `-Dcucumber.filter.tags` option to run specific scenarios based on their tags.
Always use a command prompt terminal:

- gradle test -Dcucumber.filter.tags="@login" 
  → Runs all scenarios that have the `@login` tag.

- gradle test -Dcucumber.filter.tags="@products"
  → Runs all scenarios that have the `@products` tag.

- gradle test -Dcucumber.filter.tags="not @login"
  → Runs all scenarios that **do not** have the `@login` tag.

- gradle test -Dcucumber.filter.tags="@login or @products"
  → Runs scenarios that have **either** the `@login` or `@products` tag.

- gradle test -Dcucumber.filter.tags="@login and @products"
  → Runs **only** scenarios that have **both** the `@login` and `@products` tags.

- gradle test -Dcucumber.filter.tags="@products and (@2 or @3)"
  → Runs scenarios that have the `@products` tag and **either** the `@2` or `@3` tag.

- gradle test -Dcucumber.filter.tags="@login and not @products"
  → Runs scenarios that have the `@login` tag but **do not** have the `@products` tag.
