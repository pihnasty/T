package trestview.routeperspective.schemaroute.mesh;

import designpatterns.ObservableDS;
import entityProduction.Lineroute;
import entityProduction.Machine;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

//TODO: сделать подписи к label + machine picture
public class MeshModel extends ObservableDS implements Observer {

    /**
     * main properties of mesh
     */
    private Integer idMesh;
    private Integer operationNumber;
    private Integer minInputBuffer;
    private Integer maxInputBuffer;
    private Integer minOutputBuffer;
    private Integer maxOutputBuffer;
    private String operationName;
    private Machine machine;
    /**
     * include another params such as equipment name, worker etc.
     * Key - param name;
     * Value - param value;
     */
    private Map<String, Object> secondaryParams;

    public MeshModel(Machine machine, Lineroute lineroute) {

        this.machine = machine;
        this.minInputBuffer = lineroute.getInputBufferMin();
        this.maxInputBuffer = lineroute.getInputBufferMax();
        this.minOutputBuffer = lineroute.getOutputBufferMin();
        this.maxOutputBuffer = lineroute.getOutputBufferMax();
        this.operationName = lineroute.getName();
        this.secondaryParams = new HashMap<>();
    }

    public Machine getMachine( ) {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Integer getIdMesh( ) {
        return idMesh;
    }

    public void setIdMesh(Integer idMesh) {
        this.idMesh = idMesh;
    }

    public Integer getOperationNumber( ) {
        return operationNumber;
    }

    public void setOperationNumber(Integer operationNumber) {
        this.operationNumber = operationNumber;
    }

    public Integer getMinInputBuffer( ) {
        return minInputBuffer;
    }

    public void setMinInputBuffer(Integer minInputBuffer) {
        this.minInputBuffer = minInputBuffer;
    }

    public Integer getMaxInputBuffer( ) {
        return maxInputBuffer;
    }

    public void setMaxInputBuffer(Integer maxInputBuffer) {
        this.maxInputBuffer = maxInputBuffer;
    }

    public Integer getMinOutputBuffer( ) {
        return minOutputBuffer;
    }

    public void setMinOutputBuffer(Integer minOutputBuffer) {
        this.minOutputBuffer = minOutputBuffer;
    }

    public Integer getMaxOutputBuffer( ) {
        return maxOutputBuffer;
    }

    public void setMaxOutputBuffer(Integer maxOutputBuffer) {
        this.maxOutputBuffer = maxOutputBuffer;
    }

    public String getOperationName( ) {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Map<String, Object> getSecondaryParams( ) {
        return secondaryParams;
    }

    public void setSecondaryParams(Map<String, Object> secondaryParams) {
        this.secondaryParams = secondaryParams;
    }

    public Image getMachineImage( ) {
        if (machine != null) {
            return new Image("file:" + machine.getModelmachine().getImg());
        } else {
            System.out.println("null");
            return new Image("https://www.iconexperience.com/_img/o_collection_png/green_dark_grey/512x512/plain/industrial_machine.png");
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
