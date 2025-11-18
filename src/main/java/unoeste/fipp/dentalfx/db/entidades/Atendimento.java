package unoeste.fipp.dentalfx.db.entidades;

import java.util.ArrayList;
import java.util.List;

public class Atendimento {
    public static  record  MatItem(Material material, int quant){};
    public static record ProcItem(Procedimento procedimento, int quant){};

    private String relato;
    private List<MatItem> materialList;
    private List<ProcItem> procedimentoList;
    private boolean efetivado;

    public Atendimento(String relato,boolean efetivado) {
        this.relato = relato;
        this.efetivado = efetivado;
        materialList = new ArrayList<>();
        procedimentoList = new ArrayList<>();
    }

    public String getRelato() {
        return relato;
    }

    public boolean addMaterial(int quantidade, Material material){
        return materialList.add(new MatItem(material,quantidade));
    }

    public boolean addProcedimento(int quantidade, Procedimento procedimento){
        return procedimentoList.add(new ProcItem(procedimento,quantidade));
    }
    public boolean efetivado(){
        return efetivado;
    }

    public void setMaterialList(List<MatItem> materialList) {
        this.materialList = materialList;
    }

    public void setProcedimentoList(List<ProcItem> procedimentoList) {
        this.procedimentoList = procedimentoList;
    }

    public List<MatItem> getMaterialList() {
        return materialList;
    }

    public List<ProcItem> getProcedimentoList() {
        return procedimentoList;
    }

    public void setRelato(String relato) {
        this.relato = relato;
    }
}
