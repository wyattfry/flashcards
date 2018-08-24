
# Flash Cards
Command line flash cards program to create, manage and review flash cards:

```
~~ Flash Cards ~~

- 31 card(s) loaded from cards.dat.

~~~ Main Menu ~~~
 1. add card to database
 2. practice cards from a category - not implemented
 3. practice all cards
 4. view all cards, edit, delete - part implemented
 5. save and quit

Your selection:
```

## Compile
Navigate outside the package directory `flashcards` and execute the following command:
```bash
$ mkdir -p out/production/flashcards
$ javac -d out/production/flashcards/ -cp src/ src/flashcards/FlashCards.java
```
## Run
Interactive mode:
```bash
$ java -cp out/production/flashcards/ flashcards.FlashCards
```
A ``cards.dat`` file will be created if none exists to store your flash cards.
You can specify a deck file to use with the ``-f`` option, e.g.:
```bash
$ java flashcards.FlashCards -f different_deck.dat
```
To make separate decks, run without the `-f` option for the first time, make at least one card, quit, rename the `cards.dat` file, then next time, use the `-f` option with the new name.
