package JavaFxPrograms;

import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class StarterAppModel {
    
    public ObservableList getTeamMembers() {
        ObservableList teamMembers = FXCollections.observableArrayList();
        
        for(int i = 1; i <= 10000; i++){
            teamMembers.add(new Person("FirstName" + i,
            "LastName" + i,
            "Phone" + i));
        }
        
        return teamMembers;
    }
    
    public TreeItem<Person> getFamilyTree() {
        Random random = new Random();
        TreeItem<Person> root = new TreeItem();
        for (int i = 0; i < 5; i++) {
            Person parent = new Person("Parent " + i, "Lastname " + i, "Phone " + i);
            TreeItem<Person> parentItem = new TreeItem();
            for (int j = 0; j < random.nextInt(4); j++){
                Person child = new Person("Child " + i + "-" + j, "Lastname " + i, "Phone " + j);
                TreeItem<Person> childItem = new TreeItem(child);
                parentItem.getChildren().add(childItem);
                for (int k = 0; k < random.nextInt(4); k++){
                    Person grandChild = new Person("Grandchild " + i + "-" + j + "-" + k, "Lastname" + i, "Phone" + k);
                    TreeItem<Person> grandChildren = new TreeItem(grandChild);
                    childItem.getChildren().add(grandChildren);
                }
            }
            root.getChildren().add(parentItem);
        }
        return root;
    }
    
    public String getRandomWebsite(){
        String[] webSites = {
            "http://javafx.com",
            "http://sheyeoladejo.com",
            "http://steveonjava.com",
            "http://javafxpert.com",
            "http://google.com",
            "http://youtube.com",
        };
        int randomIdx = (int) (Math.random() * webSites.length);
        return webSites[randomIdx];
    }
    
    public ObservableList listViewItems = FXCollections.observableArrayList();
    public ObservableList choiceBoxItems = FXCollections.observableArrayList(
            "Choice A",
            "Choice B",
            "Choice C",
            "Choice D"
    );
    
    public double maxRpm = 8000.0;
    public DoubleProperty rpm = new SimpleDoubleProperty(0);
    
    public double maxKph = 8000.0;
    public DoubleProperty kph = new SimpleDoubleProperty(0);
    
}
