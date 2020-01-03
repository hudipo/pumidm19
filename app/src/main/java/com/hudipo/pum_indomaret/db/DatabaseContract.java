package com.hudipo.pum_indomaret.db;

public class DatabaseContract {

    public class CartResponseFullColumns {
        static final String TABLE_CART_RESPONSE_FULL = "cart_response_full";
        public static final String ID_CART_RESPONSE_FULL = "_id_cart_response_full";
        public static final String TRX_NUMBER_FULL = "trx_number_full";
        public static final String SUBMIT_RESPONSE_ITEM_FULL = "submit_response_item_full";
    }

    public class CartResponsePartialColumns {
        static final String TABLE_CART_RESPONSE_PARTIAL = "cart_response_partial";
        public static final String ID_CART_RESPONSE_PARTIAL = "_id_cart_response_partial";
        public static final String TRX_NUMBER_PARTIAL = "trx_number_partial";
        public static final String SUBMIT_RESPONSE_ITEM_PARTIAL = "submit_response_item_partial";
    }
}
