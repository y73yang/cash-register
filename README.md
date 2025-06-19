# Cash Register

A Java application that simulates a grocery store cash register with support for item-level and spend-and-save discounts.

---

## Technologies Used

- Java
- Lombok
- JUnit 5
- Maven

---

## How to Run

1. Build the project using Maven:

   ```bash
   mvn clean install
   ```

2. Run the executable JAR:

   ```bash
   java -jar target/cash-register-*-aio.jar <item_file> <discount_file> <shopping_item_file>
   ```

Sample input files are located in `src/test/resources/`.

---

## Input Files

The application requires three CSV files:

1. `<item_file>` – Details about items available for sale at the grocery store
2. `<discount_file>` – item-level(dollars-off, bulk) or spend-and-save discount rules
3. `<shopping_item_file>` – items being purchased

---

## Special Notes

- **Duplicate handling**:
  - If multiple entries in the **<item_file>** share the same `item_id`, the one appearing **later** (i.e., lower in the file) will overwrite previous entries.
  - If multiple entries in the **<shopping_item_file>** share the same `item_id`, their **quantities will be summed**.

- **There are 3 types of discounts**:

  - `dollars-off discount` - (e.g., "Buy 2 identical items, Save $1")
  - `bulk discount` - (e.g., "Buy 3, Get 1 Free")
  - `spend-and-save discounts` (e.g., "Spend $100, Save $10")

- **Discount rules**:
    - For item-level discounts, **only the highest applicable discount** will be applied. **Discounts do not stack**.
    - For spend-and-save discounts, **only the highest applicable discount** will be applied. **Discounts do not stack**.
    - The spend-and-save discounts will be applied **after** the item-level discounts.

- **Data validation & rules**:
    - A per-item discount will be **ignored** if the item is **sold per kg**.
    - Discounts and shopping items are **ignored** if their corresponding `item_id` is **not found** in the item list.
    - A shopping item will be **ignored** if it has a **non-integer quantity** while the item is measured by `each`.
    - Invalid entries in any input file are safely **ignored** during import. See `DataImporter` and `InputValidator` classes for logic details.

- **Rounding**:
    - All price and bill values are rounded to **two decimal places**.

---

## Author

Yi Fan Yang\
Feel free to reach out for questions, collaboration, or suggestions!

