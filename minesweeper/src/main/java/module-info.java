module com.jfxgame {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.jfxgame to javafx.fxml;
    exports com.jfxgame;
}
