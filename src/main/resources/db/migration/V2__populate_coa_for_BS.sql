INSERT INTO accounting.code_of_account ("code", "title", "level", "element", "type") VALUES
-- 資産 (Assets) - Debit
(1101, '現金', 1, 'Assets', 'Debit'),
(1102, '普通預金', 1, 'Assets', 'Debit'),
(1103, '定期預金', 1, 'Assets', 'Debit'),
(1111, '売掛金', 1, 'Assets', 'Debit'),
(1112, '受取手形', 1, 'Assets', 'Debit'),
(1121, '有価証券', 1, 'Assets', 'Debit'),
(1131, '商品', 1, 'Assets', 'Debit'),
(1141, '前払金', 1, 'Assets', 'Debit'),
(1142, '立替金', 1, 'Assets', 'Debit'),
(1143, '仮払金', 1, 'Assets', 'Debit'),
(1151, '貸付金', 1, 'Assets', 'Debit'),
(1201, '建物', 1, 'Assets', 'Debit'),
(1202, '車両運搬具', 1, 'Assets', 'Debit'),
(1203, '工具器具備品', 1, 'Assets', 'Debit'),
(1211, '土地', 1, 'Assets', 'Debit'),
(1221, '敷金', 1, 'Assets', 'Debit'),

-- 負債 (Liabilities) - Credit
(2101, '買掛金', 1, 'Liabilities', 'Credit'),
(2102, '支払手形', 1, 'Liabilities', 'Credit'),
(2111, '短期借入金', 1, 'Liabilities', 'Credit'),
(2121, '未払金', 1, 'Liabilities', 'Credit'),
(2131, '前受金', 1, 'Liabilities', 'Credit'),
(2132, '預り金', 1, 'Liabilities', 'Credit'),
(2133, '仮受金', 1, 'Liabilities', 'Credit'),
(2201, '長期借入金', 1, 'Liabilities', 'Credit'),

-- 純資産 (Equity)
(3101, '元入金', 1, 'Equity', 'Credit'),
(3102, '事業主貸', 1, 'Equity', 'Debit'),
(3103, '事業主借', 1, 'Equity', 'Credit');