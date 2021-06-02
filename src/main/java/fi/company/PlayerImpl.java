package fi.company;


import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Optional;

@SessionScoped
public class PlayerImpl implements Player, Serializable {

    private String name = null;
    private int score = 20;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }
}
