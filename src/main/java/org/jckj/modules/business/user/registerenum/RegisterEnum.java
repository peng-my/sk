package org.jckj.modules.business.user.registerenum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RegisterEnum {
    jpy("jpy","1"),
    cny("cny","2"),
    usd("usd","3");
    private String code;
    private String value;
    /**
     * 根据code获取去value
     * @param code
     * @return
     */
    public static String getValueByCode(String code){
        for(RegisterEnum platformFree:RegisterEnum.values()){
            if(code.equals(platformFree.getCode())){
                return platformFree.getValue();
            }
        }
        return  null;
    }
}
