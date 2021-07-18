package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.CardTypes;
import model.cards.magiccard.MagicCard;
import model.cards.magiccard.MagicCardStatuses;
import model.cards.monstercard.MonsterCard;
import model.cards.monstercard.MonsterCardAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Database {
    public static void prepareGame() {
        new File("src/main/resources/players/avatars").mkdirs();

        addCardsToGame();
        readPlayersDataFromDatabase();
        prepareSomeAvatars();
    }

    private static void addCardsToGame() {
        try {
            FileReader monsterCardFileReader = new FileReader("src/main/resources/cards/Monster Upgraded.csv");
            CSVReader monsterCardCSVReader = new CSVReaderBuilder(monsterCardFileReader).withSkipLines(1).build();

            String[] monsterCardData;
            while ((monsterCardData = monsterCardCSVReader.readNext()) != null) {
                createNewMonsterCard(monsterCardData);
            }


            FileReader magicCardFileReader = new FileReader("src/main/resources/cards/SpellTrap Upgraded.csv");
            CSVReader magicCardCSVReader = new CSVReaderBuilder(magicCardFileReader).withSkipLines(1).build();

            String[] magicCardData;
            while ((magicCardData = magicCardCSVReader.readNext()) != null) {
                createNewMagicCard(magicCardData);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(0);
        }
    }

    public static void createNewMonsterCard(String[] data) {
        String name = data[0];
        short level = Short.parseShort(data[1]);
        MonsterCardAttributes monsterCardAttributes = MonsterCardAttributes.valueOf(data[2]);
        String monsterType = data[3];
        CardTypes cardType = CardTypes.valueOf(data[4].toUpperCase());
        int attackPoints = Integer.parseInt(data[5]);
        int defensePoints = Integer.parseInt(data[6]);
        String description = data[7];
        int price = Integer.parseInt(data[8]);
        String frontImageAddress = "/Project-Assets-1.0.0/Assets/Cards/Monsters/" + getNameForFrontImageAddress(name) + ".jpg";

        new MonsterCard(name, level, monsterCardAttributes, monsterType, cardType, attackPoints,
                defensePoints, description, price, frontImageAddress);
    }

    public static void createNewMagicCard(String[] data) {
        String name = data[0];
        CardTypes cardType = CardTypes.valueOf(data[1].toUpperCase());
        String icon = data[2];
        String description = data[3];
        MagicCardStatuses status = MagicCardStatuses.valueOf(data[4].toUpperCase());
        int price = Integer.parseInt(data[5]);
        String frontImageAddress = "/Project-Assets-1.0.0/Assets/Cards/SpellTrap/" + getNameForFrontImageAddress(name) + ".jpg";

        new MagicCard(name, cardType, icon, description, status, price, frontImageAddress);
    }

    public static String getNameForFrontImageAddress(String name) {
        name = name.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", "");
        if (name.startsWith("Terratiger")) name = "Terratiger";

        return name;
    }

    public static void readPlayersDataFromDatabase() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        File file = new File("src/main/resources/players");
        FilenameFilter filenameFilter = (direction, name) -> name.endsWith(".json");
        String[] filesName = file.list(filenameFilter);

        if (filesName == null) return;
        for (String fileName : filesName) {
            try {
                FileReader fileReader = new FileReader("src/main/resources/players/" + fileName);
                Player player = gson.fromJson(fileReader, Player.class);
                fileReader.close();
                Player.addPlayerToAllPlayers(player);
                addBoughtCardsToPlayer(player);
                addDeckCardsToPlayer(player);
                handleActivatedDeck(player);
                handleAvatar(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static void addBoughtCardsToPlayer(Player player) {
        ArrayList<Card> boughtCards = player.getBoughtCards();
        for (int i = 0; i < boughtCards.size(); i++) {
            Card fakeCard = boughtCards.get(0);
            Card originalCard = Card.getCardByName(fakeCard.getName());
            boughtCards.remove(fakeCard);
            player.createCardToBoughtCards(originalCard);
        }
    }

    private static void addDeckCardsToPlayer(Player player) {
        ArrayList<Deck> allDecks = player.getAllDecks();
        for (Deck deck: allDecks) {
            addDeckMainCardsToPlayer(deck);
            addDeckSideCardsToPlayer(deck);
        }
    }

    private static void addDeckMainCardsToPlayer(Deck deck) {
        ArrayList<Card> mainCards = deck.getMainCards();
        for (int i = 0; i < mainCards.size(); i++) {
            Card fakeCard = mainCards.get(0);
            Card originalCard = Card.getCardByName(fakeCard.getName());
            mainCards.remove(fakeCard);
            if (originalCard instanceof MonsterCard)
                deck.addCardToMainDeck(new MonsterCard((MonsterCard) originalCard));
            else deck.addCardToMainDeck(new MagicCard((MagicCard) originalCard));
        }
    }

    private static void addDeckSideCardsToPlayer(Deck deck) {
        ArrayList<Card> sideCards = deck.getSideCards();
        for (int i = 0; i < sideCards.size(); i++) {
            Card fakeCard = sideCards.get(0);
            Card originalCard = Card.getCardByName(fakeCard.getName());
            sideCards.remove(fakeCard);
            if (originalCard instanceof MonsterCard)
                deck.addCardToSideDeck(new MonsterCard((MonsterCard) originalCard));
            else deck.addCardToSideDeck(new MagicCard((MagicCard) originalCard));
        }
    }

    private static void handleActivatedDeck(Player player) {
//        create the same reference for activated deck in "allDecks" and "activatedDeck" in player class
        if (player.getActivatedDeck() != null) {
            Deck activatedDeck = player.getDeckByName(player.getActivatedDeck().getName());
            player.setActivatedDeck(activatedDeck);
        }
    }

    private static void handleAvatar(Player player) {
        try {
            File imageFile = new File("src/main/resources/players/avatars/" + player.getUsername() + ".png");
            player.setAvatar(ImageIO.read(imageFile));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(0);
        }
    }

    private static void prepareSomeAvatars() {
        boolean isDirectoryCreated = new File("src/main/resources/preparedAvatars").mkdir();
        if (!isDirectoryCreated) return;

        String fakeUsername = "1";
        for (int i = 0; i < 100; i++) {
            try {
                BufferedImage bufferedImage = Utils.createAvatar(fakeUsername);
                File imageFile = new File("src/main/resources/preparedAvatars/" + fakeUsername + ".png");
                ImageIO.write(bufferedImage, "png", imageFile);
                fakeUsername = fakeUsername.concat("" + i);
            } catch (IOException ignored) {
            }
        }
    }

    public static void updatePlayerInformationInDatabase(Player player) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/players/" + player.getUsername() + ".json");
            fileWriter.write(gson.toJson(player));
            fileWriter.close();

            File imageFile = new File("src/main/resources/players/avatars/" + player.getUsername() + ".png");
            ImageIO.write(player.getAvatar(), "png", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void removePlayer(Player player) {
        new File("src/main/resources/players/" + player.getUsername() + ".json").delete();
    }
}
