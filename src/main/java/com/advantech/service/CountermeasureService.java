package com.advantech.service;

import com.advantech.entity.Countermeasure;
import com.advantech.model.BasicDAO;
import com.advantech.model.CountermeasureDAO;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wei.Cheng
 */
public class CountermeasureService {

    private final CountermeasureDAO countermeasureDAO;

    protected CountermeasureService() {
        countermeasureDAO = new CountermeasureDAO();
    }

    public List<Countermeasure> getCountermeasure() {
        return countermeasureDAO.getCountermeasures();
    }

    public Countermeasure getCountermeasure(int BABid) {
        return countermeasureDAO.getCountermeasure(BABid);
    }

    public List<Map> getCountermeasureView() {
        return countermeasureDAO.getCountermeasureView();
    }

    public List<Map> getUnFillCountermeasureBabs() {
        return countermeasureDAO.getUnFillCountermeasureBabs();
    }

    public List<Map> getUnFillCountermeasureBabs(String sitefloor) {
        return countermeasureDAO.getUnFillCountermeasureBabs(sitefloor);
    }

    public List<Map> getCountermeasure(int[] BABid) {
        return countermeasureDAO.getCountermeasure(BABid);
    }

    public List<Map> getCountermeasure(String lineType, String sitefloor, String startDate, String endDate) {
        List<Map> l = countermeasureDAO.getCountermeasure(startDate, endDate);
        return BasicService.getBabService().seperateNotFilterBab(l, lineType, sitefloor);
    }

    public List<Map> getPersonalAlm(String lineType, String sitefloor, String startDate, String endDate) {
        List<Map> l = countermeasureDAO.getPersonalAlm(startDate, endDate);
        l = BasicService.getBabService().seperateNotFilterBab(l, lineType, sitefloor);
        return this.transformPersonalAlmDataPattern(l);
    }

    private List<Map> transformPersonalAlmDataPattern(List<Map> l) {
        List<Map> tList = new ArrayList();
        Map baseMap = null;
        int baseId = 0;

        for (int i = 0; i < l.size(); i++) {
            Map m = l.get(i);
            if (i == 0) {
                baseMap = m;
                baseId = (int) m.get("id");
                baseMap.put("USER_ID" + m.get("station"), m.get("USER_ID"));
                baseMap.put("亮燈頻率" + m.get("station"), m.get("failPercent(Personal)"));
                removeUnusedKeyInMap(baseMap);
            } else if ((int) m.get("id") != baseId) {
                tList.add(baseMap);
                baseMap = m;
                baseMap.put("USER_ID" + m.get("station"), m.get("USER_ID"));
                baseMap.put("亮燈頻率" + m.get("station"), m.get("failPercent(Personal)"));
                removeUnusedKeyInMap(baseMap);
                baseId = (int) m.get("id");
            } else if (baseMap != null && (int) m.get("id") == baseId) {
                baseMap.put("USER_ID" + m.get("station"), m.get("USER_ID"));
                baseMap.put("亮燈頻率" + m.get("station"), m.get("failPercent(Personal)"));
                if (i == (l.size() - 1)) {
                    tList.add(baseMap);
                }
            }
        }
        return tList;
    }

    private Map removeUnusedKeyInMap(Map m) {
        m.remove("USER_ID");
        m.remove("station");
        m.remove("failPercent(Personal)");
        return m;
    }

    public List<Map> getErrorCode() {
        return countermeasureDAO.getErrorCode();
    }

    public List<Map> getErrorCode(int cm_id) {
        return countermeasureDAO.getErrorCode(cm_id);
    }

    public List<Map> getActionCode() {
        return countermeasureDAO.getActionCode();
    }

    public List<Map> getEditor(int cm_id) {
        return countermeasureDAO.getEditor(cm_id);
    }

    public boolean insertCountermeasure(int BABid, List<String> errorCode_id, String solution, String editor) {
        return countermeasureDAO.insertCountermeasure(BABid, solution, errorCode_id, editor);
    }

    public boolean updateCountermeasure(int id, List<String> errorCode_id, String solution, String editor) {
        if (this.getCountermeasure(id) == null) {
            return this.insertCountermeasure(id, errorCode_id, solution, editor);
        } else {
            return countermeasureDAO.updateCountermeasure(id, solution, errorCode_id, editor);
        }
    }

    public boolean deleteCountermeasure(int id) {
        return countermeasureDAO.deleteCountermeasure(id);
    }

    public static void main(String arg0[]) {
        BasicDAO.dataSourceInit1();
        int[] BABid = {
            1018,
            1017,
            1016,
            1015,
            1014,
            1013
        };
        out.println(BasicService.getCountermeasureService().getCountermeasure(BABid));
    }
}
