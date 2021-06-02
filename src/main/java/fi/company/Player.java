package fi.company;


import java.util.Optional;

public interface Player {
    public void setName(String name);
    public Optional<String> getName();
    public void setScore(int score);
    public int getScore();
}
