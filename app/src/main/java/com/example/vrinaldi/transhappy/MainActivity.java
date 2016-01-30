package com.example.vrinaldi.transhappy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;
import android.widget.Button;

import java.util.List;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.LocalOpenTransportRepository;
import ch.schoeb.opendatatransport.model.Connection;
import ch.schoeb.opendatatransport.model.ConnectionList;
import ch.schoeb.opendatatransport.model.ConnectionStation;

public class MainActivity extends AppCompatActivity {

    private EditText fromParam;
    private EditText toParam;
    private Button searchButton;
    private TextView res1Off;
    private TextView res1On;
    private TextView res1Duration;
    private TextView res1Trail;
    private IOpenTransportRepository transRep = new LocalOpenTransportRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromParam = (EditText) findViewById(R.id.searchFromText);
        toParam = (EditText) findViewById(R.id.searchToText);

        searchButton = (Button) findViewById(R.id.sarchButton);
        searchButton.setOnClickListener(new SearchButtonListener());

        res1Off = (TextView) findViewById(R.id.firstResOff);
        res1On = (TextView) findViewById(R.id.firstResOn);
        res1Duration = (TextView) findViewById(R.id.firstResDuration);
        res1Trail = (TextView) findViewById(R.id.firstResTrail);
    }


    private class SearchButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String from = fromParam.getText().toString();
            String to = toParam.getText().toString();
            ConnectionList connectionList = transRep.searchConnections(from, to);
            List<Connection> connection = connectionList.getConnections();
            Connection res1 = connection.get(0);
            if(res1 != null) {
                ConnectionStation fromStation = res1.getFrom();
                ConnectionStation toStation = res1.getTo();

                if(fromStation != null) {
                    res1Off.setText(fromStation.getDeparture());
                    res1Trail.setText(fromStation.getPlatform());
                } else {
                    res1Off.setText("12:10");
                    res1Trail.setText("1");
                }
                if(toStation != null) {
                    res1On.setText(toStation.getArrival());
                } else {
                    res1On.setText("13:24");
                }
                res1Duration.setText(res1.getDuration());
            }

        }

    }
}
