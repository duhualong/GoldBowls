package haowei.computer.goldbowl.data.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * ProvinceTable

 */
public class ProvinceTable extends RealmObject {
  @PrimaryKey
  private int id;
  @Required
  private String province;
  private int provinceNum;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public int getProvinceNum() {
    return provinceNum;
  }

  public void setProvinceNum(int provinceNum) {
    this.provinceNum = provinceNum;
  }
}
