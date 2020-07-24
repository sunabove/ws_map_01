package gmlviewer.gis.file.shape;

public abstract class EsriShape {

    private int recordNumber;

    public EsriShape(int recordNumber) {

        this.recordNumber = recordNumber;

    }

    public int getRecordNumber() {

       return recordNumber;

    }

}
