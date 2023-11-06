package cr.ac.una.clinicauna.animations;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author estebannajera
 */
public abstract class Animate {

    public static final int INDEFINITE = -1;
    private Timeline timeline;
    private boolean reset;
    private Node node;
    private Animate nextAnimation;
    private boolean hasNextAnimation;

    public Animate(Node node) {
        super();
        setNode(node);

    }

    public Animate() {
        hasNextAnimation = false;
        this.reset = false;

    }

    private Animate onFinished() {
        if (reset) {
            resetNode();
        }
        if (this.nextAnimation != null) {
            this.nextAnimation.play();
        }
        return this;
    }

    public Animate playOnFinished(Animate animation) {
        setNextAnimation(animation);
        return this;

    }

    public Animate setResetOnFinished(boolean reset) {
        this.reset = reset;
        return this;
    }

    public void play() {
        timeline.play();
    }

    public Animate stop() {
        timeline.stop();
        return this;
    }

    protected abstract Animate resetNode();

    /**
     * Function to initialize the timeline
     */
    protected abstract void initTimeline();

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public boolean isResetOnFinished() {
        return reset;
    }

    protected void setReset(boolean reset) {
        this.reset = reset;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        initTimeline();
        timeline.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(Animation.Status.STOPPED)) {
                onFinished();
            }

        });
    }

    public Animate getNextAnimation() {
        return nextAnimation;
    }

    protected void setNextAnimation(Animate nextAnimation) {
        hasNextAnimation = true;
        this.nextAnimation = nextAnimation;
    }

    public boolean hasNextAnimation() {
        return hasNextAnimation;
    }

    protected void setHasNextAnimation(boolean hasNextAnimation) {
        this.hasNextAnimation = hasNextAnimation;
    }

    public Animate setCycleCount(int value) {
        this.timeline.setCycleCount(value);
        return this;
    }

    public Animate setSpeed(double value) {
        this.timeline.setRate(value);
        return this;
    }

    public Animate setDelay(Duration value) {
        this.timeline.setDelay(value);
        return this;
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        this.timeline.setOnFinished(value);
    }
}
