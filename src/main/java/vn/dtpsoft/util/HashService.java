package vn.dtpsoft.util;

import org.hashids.Hashids;

public class HashService {

    public static String generateHash(String salt, long id, int len){
        Hashids hashids = new Hashids(salt, len);
        return hashids.encode(id);
    }
}
