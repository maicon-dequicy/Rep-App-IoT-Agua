package br.com.aplicativomonitoramentoagua;
public class DataPoint
{
    float xValue;
    String yValue;

    public DataPoint(float xValue, String yValue)
    {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public float getxValue() {
        return this.xValue;
    }

    public String getyValue() {
        return this.yValue;
    }
}
