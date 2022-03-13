/**
 * Plastic Pipe inherits from Pipe
 * @author: Suren Fernando
 * StudentID: 1138702
 */
public class PlasticPipes extends Pipes {

    /**
     * Create plastic pipe
     * @param pipeImg filename string of the location of plastic pipe image
     * @param pipeY Y coordinate the pipe should be spawned at
     */
    public PlasticPipes(String pipeImg, double pipeY) {
        super(pipeImg, pipeY, "plastic");
    }
}
