# Console UI test plan

These cases verify the requested console behavior for `Minerva.java`. They run in order and stop at the first failure. The expected blocks describe each user-visible response; the startup banner and prompts remain part of the session transcript.

## Test case: Add a todo

**Aim:** Confirm that adding a todo stores and displays the task without the command prefix.

**Command:**

```powershell
java -cp out Minerva
```

**Console input:**

```text
todo borrow book
bye
```

**Expected console output:**

```text
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
Bye. And I hope to see you again soon!
```

## Test case: List tasks

**Aim:** Confirm that the task list displays stored todo, deadline, and event entries in order.

**Command:**

```powershell
java -cp out Minerva
```

**Console input:**

```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

**Expected console output:**

```text
Here are the tasks in your list:
1. [T][ ] borrow book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
Bye. And I hope to see you again soon!
```

## Test case: Add a deadline

**Aim:** Confirm that `/by` separates the deadline description from its date text.

**Command:**

```powershell
java -cp out Minerva
```

**Console input:**

```text
deadline return book /by Sunday
bye
```

**Expected console output:**

```text
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 1 tasks in the list.
Bye. And I hope to see you again soon!
```

## Test case: Add an event

**Aim:** Confirm that `/from` and `/to` separate the event description and time text.

**Command:**

```powershell
java -cp out Minerva
```

**Console input:**

```text
event project meeting /from Mon 2pm /to 4pm
bye
```

**Expected console output:**

```text
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 tasks in the list.
Bye. And I hope to see you again soon!
```
