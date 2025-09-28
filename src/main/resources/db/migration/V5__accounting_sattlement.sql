INSERT INTO accounting.code_of_account ("code", "title", "level", "element", "type") VALUES

-- 純資産 (Equity)
(3111, '利益剰余金', 0, 'Equity', 'Credit'),
(3112, '当期純利益', 0, 'Equity', 'Credit'),
(3113, '当期純損失', 0, 'Equity', 'Debit'),
(3114, '繰越利益剰余金', 0, 'Equity', 'Credit'),

-- その他 (Other)
-- 税金・保険 (Taxes & Social Insurance)
(6211, '法人税', 0, 'Other', 'Debit'),
(6212, '地方法人税', 0, 'Other', 'Debit'),
(6213, '法人住民税', 0, 'Other', 'Debit'),
(6214, '法人事業税', 0, 'Other', 'Debit'),
(6215, '復興特別法人税', 0, 'Other', 'Debit'),
(6216, '固定資産税', 0, 'Other', 'Debit'),
(6217, '事業所税', 0, 'Other', 'Debit'),
(6218, '不動産取得税', 0, 'Other', 'Debit'),
(6219, '自動車税', 0, 'Other', 'Debit'),
(6220, '印紙税', 0, 'Other', 'Debit'),

-- 消費税（仮受・仮払）
(1301, '仮払消費税', 0, 'Assets', 'Debit'),
(2301, '仮受消費税', 0, 'Liabilities', 'Credit'),

-- 源泉税・住民税（預り金）
(213201, '源泉所得税預り金', 0, 'Liabilities', 'Credit'),
(213202, '住民税預り金（従業員）', 0, 'Liabilities', 'Credit'),

-- 個人向け税（必要に応じて使用）
(6221, '所得税（源泉分）', 0, 'Other', 'Debit'),
(6222, '復興特別所得税', 0, 'Other', 'Debit'),
(6223, '住民税（個人）', 0, 'Other', 'Debit'),

-- 法定福利費（事業者負担分）および預り金（従業員負担分）
(6311, '健康保険料（法定福利費）', 0, 'Other', 'Debit'),
(6312, '厚生年金保険料（法定福利費）', 0, 'Other', 'Debit'),
(6313, '雇用保険料（法定福利費）', 0, 'Other', 'Debit'),
(6314, '労災保険料（法定福利費）', 0, 'Other', 'Debit'),
(6315, '介護保険料（法定福利費）', 0, 'Other', 'Debit'),

(2311, '健康保険料預り金', 0, 'Liabilities', 'Credit'),
(2312, '厚生年金保険料預り金', 0, 'Liabilities', 'Credit'),
(2313, '雇用保険料預り金', 0, 'Liabilities', 'Credit'),
(2314, '労災保険料預り金', 0, 'Liabilities', 'Credit'),
(2315, '介護保険料預り金', 0, 'Liabilities', 'Credit');