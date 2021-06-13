package model;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> allPlayers;

    static {
        allPlayers = new ArrayList<>();
    }

    private ArrayList<Card> boughtCards;
    private ArrayList<Deck> allMainDecks;
    private Deck sideDeck;
    private Deck activatedDeck;
    private String username;
    private String password;
    private String nickname;
    private transient Deck allPlayerCard;
    private transient ArrayList<Deck> allDeck = new ArrayList<>();
    private transient ArrayList<Deck> gameDecks = new ArrayList<>();
    private long score;
    private long money;
    private int lifePoint;

    {
        boughtCards = new ArrayList<>();
        allMainDecks = new ArrayList<>();
        sideDeck = new Deck();
        activatedDeck = null;
        score = 0;
        money = 0;
    }

    public Player(String username, String password, String nickname) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        allPlayers.add(this);
        addPlayerToDataBase(this);
    }

    private static void addPlayerToDataBase(Player player) {

    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public int getLifePoint() {
        return lifePoint;
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

    public void increaseScore(long score) {
        this.score += score;
    }

    public void decreaseScore(long score) {
        this.score -= score;
    }

    public void increaseMoney(long money) {
        this.money += money;
    }

    public void decreaseMoney(long money) {
        this.money -= money;
    }

    public void addCardToBoughtCards(Card card) {
        this.boughtCards.add(card);
    }

    public void addAmountToLifePoint(int amount){
        this.lifePoint+=amount;
    }

    public void addMainDeck(String deckName) {
        Deck mainDeck = new Deck(deckName);
        this.allMainDecks.add(mainDeck);
    }

    public Boolean isMainDeckExist(String deckName) {
        for (Deck deck : allMainDecks) {
            if (deck.getName().equals(deckName)) return true;
        }
        return false;
    }

    public Deck getActiveDeck() {
        return activatedDeck;
    }

    public void setActiveDeck(Deck activatedDeck) {
        if (this.activatedDeck != null)
            this.activatedDeck.setActive(false);
        this.activatedDeck = activatedDeck;
        activatedDeck.setActive(true);
    }

    public ArrayList<Deck> getAllDeck() {
        if (allDeck == null)
            return (allDeck = new ArrayList<>());
        return allDeck;
    }

    public void setAllDeck(ArrayList<Deck> allDeck) {
        this.allDeck = allDeck;
    }

    public Deck getAllPlayerCard() {
        return allPlayerCard;
    }

    public void addCardToAllPlayerCard(Card card) {
        this.allPlayerCard.getMainCards().add(card);
    }

    public int getMoney() {
        return money;
    }

    public void setAllPlayerCard(Deck allPlayerCard) {
        this.allPlayerCard = allPlayerCard;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void deleteDeck(Deck deck) {
        allDeck.remove(deck);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public void deleteMainDeck(String deckName) {
        Deck mainDeck = getDeckByName(deckName);
        if (mainDeck != null) {
            boughtCards.addAll(mainDeck.getMainCards());
            allMainDecks.remove(mainDeck);
        }
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : allMainDecks) {
            if (deck.getName().equals(deckName)) return deck;
        }
        return null;
    }

    public int compareTo(Player player) {
        if (this.score > player.score)
            return -1;
        if (this.score < player.score)
            return 1;
        if (this.nickname.compareTo(player.getNickname()) > 0)
            return -1;
        if (this.nickname.compareTo(player.getNickname()) < 0)
            return 1;
        return 0;
    }

    public void activateADeck(String deckName) {
        Deck deck = getDeckByName(deckName);
        if (deck != null) activatedDeck = deck;
    }
    public void activateADeck(Deck activatedDeck) {
        if (this.activatedDeck != null)
            this.activatedDeck.setActive(false);
        this.activatedDeck = activatedDeck;
        activatedDeck.setActive(true);
    }

    public void addCardToMainDeck() {
//        TODO: ???? but remember to remove this card from boughtCards :)
    }

    public void removeACard() {
//        TODO: ???? but remember to add this card from boughtCards :)
    }
}

