INSERT INTO t_inventory (sku_code, quantity)
VALUES
    ('Apple_iPhone_14', 50),
    ('Samsung_Galaxy_S22', 30),
    ('OnePlus_Nord_2', 20),
    ('Sony_Bravia_TV', 15),
    ('LG_Refrigerator', 25),
    ('Nike_Air_Max_Shoes', 100),
    ('Adidas_Superstar_Shoes', 75),
    ('MacBook_Pro_16', 10),
    ('Dell_XPS_13', 20),
    ('HP_Spectre_x360', 15)
ON CONFLICT (sku_code) DO NOTHING;

-- If necessary, you can also add more insertions
INSERT INTO t_inventory (sku_code, quantity)
VALUES
    ('Bose_Headphones_700', 40),
    ('Sony_WH-1000XM4', 35)
ON CONFLICT (sku_code) DO NOTHING;

INSERT INTO t_inventory (sku_code, quantity)
VALUES
    ('My_item', 40)
ON CONFLICT (sku_code) DO NOTHING;

