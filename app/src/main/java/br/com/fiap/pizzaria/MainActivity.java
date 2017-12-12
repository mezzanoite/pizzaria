package br.com.fiap.pizzaria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.fiap.pizzaria.model.Pedido;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvCliente)
    TextView cliente;

    @BindView(R.id.cbBacon)
    CheckBox cbBacon;

    @BindView(R.id.cbAtum)
    CheckBox cbAtum;

    @BindView(R.id.cbFrango)
    CheckBox cbFrango;

    @BindView(R.id.cbCalabresa)
    CheckBox cbCalabresa;

    @BindView(R.id.rgTamanho)
    RadioGroup rgTamanho;

    @BindView(R.id.spPagamentos)
    Spinner spPagamentos;

    private String nomeUsuario;

    Pedido pedido = new Pedido();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            nomeUsuario = getIntent().getStringExtra(LoginActivity.CLIENTE);
        }
        cliente.setText(nomeUsuario);
        setListenerCheckbox(cbAtum);
        setListenerCheckbox(cbBacon);
        setListenerCheckbox(cbCalabresa);
        setListenerCheckbox(cbFrango);

    }

    @OnClick(R.id.btFecharPedido)
    public void fecharPedido() {
        pedido.setCliente(nomeUsuario);
        pedido.setTamanho(getTamanhoSelecionado());
        pedido.setFormaPagamento(spPagamentos.getSelectedItem().toString());

        Intent i = new Intent(this, ConfirmarPedidoActivity.class);
        i.putExtra("PEDIDO", pedido);
        startActivity(i);
    }

    private void setListenerCheckbox(final CheckBox checkbox) {
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pedido.addSabor(checkbox.getText().toString());
                } else {
                    pedido.removeSabor(checkbox.getText().toString());
                }
            }
        });
    }

    public String getTamanhoSelecionado() {
        return ((RadioButton) findViewById(rgTamanho.getCheckedRadioButtonId())).getText().toString();
    }

}
