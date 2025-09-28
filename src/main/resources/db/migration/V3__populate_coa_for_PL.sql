INSERT INTO accounting.code_of_account ("code", "title", "level", "element", "type") VALUES
-- 収益 (Revenue)
    (4101, '売上', 1, 'Revenue', 'Credit'),
    (4201, '雑収入', 1, 'Revenue', 'Credit'),

-- 費用 (Expenses)
-- 売上原価 : 決算書に一括計算されるため、補助科目として設定
    (5101, '期首商品棚卸高', 1, 'Expenses', 'Debit'),
    (5102, '仕入', 1, 'Expenses', 'Debit'),
    (5103, '期末商品棚卸高', 1, 'Expenses', 'Debit'),

-- 営業費用及び一般管理費
    (5201, '給料賃金', 1, 'Expenses', 'Debit'),
    (5202, '外注工賃', 1, 'Expenses', 'Debit'),
    (5203, '減価償却費', 1, 'Expenses', 'Debit'),
    (5204, '貸倒金', 1, 'Expenses', 'Debit'),
    (5205, '地代家賃', 1, 'Expenses', 'Debit'),
    (5206, '利子割引料', 1, 'Expenses', 'Debit'),
    (5207, '租税公課', 1, 'Expenses', 'Debit'),
    (5208, '荷造運賃', 1, 'Expenses', 'Debit'),
    (5209, '水道光熱費', 1, 'Expenses', 'Debit'),
    (5210, '旅費交通費', 1, 'Expenses', 'Debit'),
    (5211, '通信費', 1, 'Expenses', 'Debit'),
    (5212, '広告宣伝費', 1, 'Expenses', 'Debit'),
    (5213, '接待交際費', 1, 'Expenses', 'Debit'),
    (5214, '損害保険料', 1, 'Expenses', 'Debit'),
    (5215, '修繕費', 1, 'Expenses', 'Debit'),
    (5216, '消耗品費', 1, 'Expenses', 'Debit'),
    (5217, '福利厚生費', 1, 'Expenses', 'Debit'),
    (5218, '新聞図書費', 1, 'Expenses', 'Debit'),
    (5219, '支払手数料', 1, 'Expenses', 'Debit'),
    (5220, '雑費', 1, 'Expenses', 'Debit');
