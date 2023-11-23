package mtmh.kafka.springboot.model;

import java.sql.Timestamp;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Appointment {
    private Long onlineappointment_id;
    private String id_no;
    private String person_name;
    private String address;
    private String email;
    private String sex;
    private String phone_no;
    private String bpjs_no;
    private Timestamp birth_date;
    private Long created_by;
    private Long updated_by;
    private Timestamp created_datetime;
    private Timestamp updated_datetime;
    private String defunct_ind;
    private String race;
    private String religion;
    private String marital_status;
    private String ethnic;
    private String tingkat_pendidikan;
    private String birth_address;
    private String occupation_group; 
    
    
    //tambahan model 
    private Long batch_regis_id;
    private Long resourcemstr_id;
    private String cardno;
    private String gender;
    private String mobile_phone;
    private Timestamp regis_date;
    private String cost_class;
    private String status;
    private Long ordertemplate_id;
    private Long patientdebtorplan_id;
    private Timestamp created_at;
    private String entity_code;
    private Long registration_id;

}
