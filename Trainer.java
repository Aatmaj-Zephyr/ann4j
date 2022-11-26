import java.util.ArrayList;

public class Trainer {
    // template pattern

    protected LayerManager myLayerManager;
    protected ArrayList<Double> expectedLayer;
    protected InputFileReader trainingFileReader;
    protected InputFileReader testingFileReader;
    private ModelEvaluator myModelEvaluator;

    double[] inputLayer;
    double label;
    private double prediction;
    private double confidence;

    Trainer() {
        this.myLayerManager = new LayerManager(parameter.getLayerArray());
        this.trainingFileReader = parameter.getTrainingFileReader();
        this.testingFileReader = parameter.getTestingFileReader();
        this.myModelEvaluator = new ModelEvaluator();

    }

    public LayerManager getLayerManager() {
        return myLayerManager;
    }

    protected void train(int epochs) {
        for (int j = 0; j <= 10; j++) {
            parameter.setTrainingFileReader("mnist_train.csv", "mnist");
            this.trainingFileReader = parameter.getTrainingFileReader();
            for (int i = 0; i <= epochs; i++) {

                // Getting the next image from the mnist database.
                trainingFileReader.next();

                // Getting the expected output array from the mnist database.
                expectedLayer = trainingFileReader.getExpectedOutputArray();

                // Getting the input array from the mnist database.
                inputLayer = trainingFileReader.getInputArray();

                // Getting the label of the image from the mnist database.
                label = trainingFileReader.getLabel();

                // System.out.print(" actual " + label);
                confidence = myLayerManager.getconfidence();

                prediction = myLayerManager.getMostSignificantNeuronAsPrediction();

                train();
                myModelEvaluator.updatePredictionData(prediction, label, confidence);

            }
        }

    }

    protected void test(int epochs) {
        for (int i = 0; i < epochs; i++) {
            // Getting the next image from the mnist database.
            testingFileReader.next();

            // Getting the expected output array from the mnist database.
            expectedLayer = testingFileReader.getExpectedOutputArray();

            // Getting the input array from the mnist database.
            inputLayer = testingFileReader.getInputArray();

            // Getting the label of the image from the mnist database.
            label = testingFileReader.getLabel();
            confidence = myLayerManager.getconfidence();
            prediction = myLayerManager.getMostSignificantNeuronAsPrediction();

            test();
            myModelEvaluator.updatePredictionData(prediction, label, confidence);
            System.out.println("Digit " + label + " is predicted as " + prediction + " with confidence " + confidence);
        }
        System.out.println("accuracy " + myModelEvaluator.getAccuracy());
    }

    protected void forwardPropagatewithExclusion(int epochs) {
        for (int i = 0; i < epochs; i++) {
            // Getting the next image from the mnist database.
            testingFileReader.next();

            // Getting the expected output array from the mnist database.
            expectedLayer = testingFileReader.getExpectedOutputArray();

            // Getting the input array from the mnist database.
            inputLayer = testingFileReader.getInputArray();

            // Getting the label of the image from the mnist database.
            label = testingFileReader.getLabel();

            forwardPropagatewithExclusion();
        }
    }

    private void forwardPropagatewithExclusion() {
        myLayerManager.forwardPropagate(); // for calculation of MSE
        myLayerManager.forwardPropagatewithExclusion();

    }

    protected void relevancePropagate(int layerNumber, int neuronNumber) {

        myLayerManager.relevancePropagate(layerNumber, neuronNumber);
    }

    private void test() {
        myLayerManager.setInputLayer(inputLayer);
        myLayerManager.setExpectedOutputArray(expectedLayer);
        myLayerManager.forwardPropagate();

    }

    protected void train() {
        // Heart of the code

        myLayerManager.setInputLayer(inputLayer);

        myLayerManager.setExpectedOutputArray(expectedLayer);

        myLayerManager.forwardPropagate();

        myLayerManager.backwardPropagate();

        prediction = myLayerManager.getMostSignificantNeuronAsPrediction();

        // System.out.println(" prediction " + prediction);
    }

}
