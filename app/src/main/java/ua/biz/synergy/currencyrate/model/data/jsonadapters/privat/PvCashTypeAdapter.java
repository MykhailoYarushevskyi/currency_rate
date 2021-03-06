package ua.biz.synergy.currencyrate.model.data.jsonadapters.privat;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.biz.synergy.currencyrate.model.data.banks.privat.CcyObject;
import ua.biz.synergy.currencyrate.model.data.banks.privat.PvCash;

public class PvCashTypeAdapter extends TypeAdapter<PvCash> {
    /**
     * Writes one JSON value (an array, object, string, number, boolean or null)
     * for {@code value}.
     *
     * @param out
     * @param value the Java object to write. May be null.
     */
    @Override
    public void write(JsonWriter out, PvCash value) throws IOException {
    
    }
    
    /**
     * Reads one JSON value (an array, object, string, number, boolean or null)
     * and converts it to a Java object. Returns the converted object.
     *
     * @param in
     * @return the converted Java object. May be null.
     */
    @Override
    public PvCash read(JsonReader in) throws IOException {
        try {
            return readPvCash(in);  // or
//            return readPvCashViaTypeToken(in); //getting a PvCash object , use {@Link TypeToken}
        } finally {
            in.close();
        }
    }
    
    private PvCash readPvCash(JsonReader reader) throws IOException {
        PvCash pvCash= new PvCash();
        List<CcyObject> ccyObjects = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            ccyObjects.add(readCcyObject(reader));
        }
        reader.endArray();
        pvCash.setCcyObjects(ccyObjects);
        return pvCash;
    }
    
    private CcyObject readCcyObject(JsonReader reader) throws IOException {
        CcyObject ccyObject = new CcyObject();
        reader.beginObject();
        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case "ccy": {
                    ccyObject.setCcy(reader.nextString());
                    break;
                }
                case "base_ccy": {
                    ccyObject.setBaseCcy(reader.nextString());
                    break;
                }
                case "buy": {
                    ccyObject.setBuy(Double.valueOf(reader.nextString()));
                    break;
                }
                case "sale": {
                    ccyObject.setSale(Double.valueOf(reader.nextString()));
                    break;
                }
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return ccyObject;
    }
    /**
     * Other way to convert JSON string in Java data object, using TypeToken
     * getting a PvCash object , use {@Link TypeToken}
     * @param reader JsonReader object
     * @return the deserialized PvCash object
     */
    private PvCash readPvCashViaTypeToken(JsonReader reader){
        Log.i(getClass().getSimpleName(), "==== method PvCash readPvCashViaListCcyObjects(JsonReader reader) STARTED");
        PvCash pvCash = new PvCash();
        Type listCcyObjectType = new TypeToken<List<CcyObject>>(){}.getType();
        List<CcyObject> listCcyObject= new Gson().fromJson(reader, listCcyObjectType);
        pvCash.setCcyObjects(listCcyObject);
        return pvCash;
    }
}