package com.extractor.type;

import com.extractor.dao.ICompaniesDAO;
import com.extractor.dao.impl.EmailOnlyDAO;
import com.extractor.dao.impl.RadaComUaDao;
import com.extractor.dao.impl.UaCompanyDao;
import com.extractor.dao.impl.UaRegionSiteDao;
import com.extractor.dao.impl.UiUaSiteDao;

/**
 * Created by Padonag on 24.11.2014.
 */
public enum Sites {
    DEFAULT {

    },
    UA_REGION_INFO {

    },
    UI_UA {

    },
    RADA_COM_UA {

    }, UA_COMPANY {

    }
    /*,
    PROM_UA {

    }*/;

    public ICompaniesDAO getDao() {
        return new EmailOnlyDAO();
    }

    @Override
    public String toString() {
        switch (this) {
            case UA_REGION_INFO:
                return "ua-region.info/";
            case UI_UA:
                return "catalog.ui.ua";
            case RADA_COM_UA:
                return "rada.com.ua/";
            /*case PROM_UA:
                return "prom.ua/";*/
            case UA_COMPANY:
                return "ua-company.com/";
            default:
                return "Other site";
        }
    }
}
