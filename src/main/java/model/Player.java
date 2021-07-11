package model;

import com.google.gson.annotations.Expose;
import controller.Database;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;

public class Player implements Comparable<Player> {
    private static final ArrayList<Player> allPlayers;

    static {
        allPlayers = new ArrayList<>();
    }

    @Expose
    private final ArrayList<Card> boughtCards;
    @Expose
    private final ArrayList<Deck> allDecks;
    private Board board;
    @Expose
    private Deck activatedDeck;
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String nickname;
    @Expose
    private long score;
    @Expose
    private long money;
    @Expose
    private int lifePoint;
    private boolean hasSummonedInTurn;
    private int wonRounds;
    private int maxLifePointDuringPlay;

    {
        boughtCards = new ArrayList<>();
        allDecks = new ArrayList<>();
        board = null;
        activatedDeck = null;
        score = 0;
        money = 100000;
        lifePoint = 8000;
        hasSummonedInTurn = false;
        wonRounds = 0;
        maxLifePointDuringPlay = 0;
    }

    public Player(String username, String password, String nickname) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        addPlayerToAllPlayers(this);
 //       Database.updatePlayerInformationInDatabase(this);
    }

    public static Boolean isNicknameExist(String nickname) {
        for (Player player : allPlayers) {
            if (player.nickname.equals(nickname)) return true;
        }
        return false;
    }

    public static Boolean isPasswordCorrect(String username, String password) {
        Player player = getPlayerByUsername(username);
        if (player == null) return false;

        return player.password.equals(password);
    }

    public static Player getPlayerByUsername(String username) {
        for (Player player : allPlayers) {
            if (player.username.equals(username)) return player;
        }
        return null;
    }

    public static void addPlayerToAllPlayers(Player player) {
        allPlayers.add(player);
    }

    public static ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getScore() {
        return score;
    }

    public long getMoney() {
        return money;
    }

    public Deck getActivatedDeck() {
        return activatedDeck;
    }

    public void setActivatedDeck(Deck activatedDeck) {
        this.activatedDeck = activatedDeck;
        Database.updatePlayerInformationInDatabase(this);
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public ArrayList<Card> getBoughtCards() {
        return boughtCards;
    }

    public Card getCardByNameFromBoughtCards(String cardName) {
        for (Card card : boughtCards) {
            if (card.getName().equals(cardName)) return card;
        }
        return null;
    }

    public void increaseScore(long score) {
        this.score += score;
        Database.updatePlayerInformationInDatabase(this);
    }

    public void increaseMoney(long money) {
        this.money += money;
        Database.updatePlayerInformationInDatabase(this);
    }

    public void decreaseMoney(long money) {
        this.money -= money;
        Database.updatePlayerInformationInDatabase(this);
    }

    public void createCardToBoughtCards(Card card) {
        if (Card.isMonsterCard(card)) {
            this.boughtCards.add(new MonsterCard((MonsterCard) card));
        } else {
            this.boughtCards.add(new MagicCard((MagicCard) card));
        }
    }

    public void removeCardFromBoughtCards(Card card) {
        boughtCards.remove(card);
    }

    public void setHasSummonedInTurn(boolean hasSummonedInTurn) {
        this.hasSummonedInTurn = hasSummonedInTurn;
    }

    public boolean getHasSummonedInTurn() {
        return this.hasSummonedInTurn;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public void setWonRounds(int wonRounds) {
        this.wonRounds = wonRounds;
    }

    public int getWonRounds() {
        return wonRounds;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public int getMaxLifePointDuringPlay() {
        return maxLifePointDuringPlay;
    }

    public void setMaxLifePointDuringPlay(int maxLifePointDuringPlay) {
        if (this.maxLifePointDuringPlay < maxLifePointDuringPlay)
            this.maxLifePointDuringPlay = maxLifePointDuringPlay;
    }

    public void decreaseLifePoint(int amount) {
        this.lifePoint -= amount;
    }

    public void increaseLifePoint(int amount) {
        this.lifePoint += amount;
    }

    public void createBoard() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : allDecks) {
            if (deck.getName().equals(deckName)) return deck;
        }
        return null;
    }

    public void addDeckToAllDecks(Deck deck) {
        if (deck != null) allDecks.add(deck);
        Database.updatePlayerInformationInDatabase(this);
    }

    public void removeDeckFromAllDecks(Deck deck) {
        allDecks.remove(deck);
        Database.updatePlayerInformationInDatabase(this);
    }

    @Override
    public int compareTo(Player player) {
        if (this.score != player.score) return (int) (this.score - player.score);
        else return player.nickname.compareToIgnoreCase(this.nickname);
    }
}