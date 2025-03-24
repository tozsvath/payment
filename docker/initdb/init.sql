CREATE TABLE account (
    account_number INT PRIMARY KEY,
    version INT NOT NULL
);

CREATE TABLE public.ledger (
	id uuid NOT NULL,
	transaction_id uuid NOT NULL,
	account_number int8 NOT NULL,
	amount numeric(19, 2) NOT NULL,
	type varchar(50) NOT NULL,
	created_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	CONSTRAINT ledger_pkey PRIMARY KEY (id)
);


CREATE INDEX idx_ledger_entry_transaction ON ledger (transaction_id);
CREATE INDEX idx_ledger_entry_account ON ledger (account_number);


INSERT INTO public.account
(account_number, "version")
VALUES(1, 0);
INSERT INTO public.account
(account_number, "version")
VALUES(2, 0);
INSERT INTO public.account
(account_number, "version")
VALUES(3, 12);
INSERT INTO public.account
(account_number, "version")
VALUES(4, 456);
INSERT INTO public.account
(account_number, "version")
VALUES(5, 233);
INSERT INTO public.account
(account_number, "version")
VALUES(6, 86);

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('9d4ef571-d33e-440b-8e92-5cdb563baa6a'::uuid, '169aa087-c955-452a-b54d-9a95fc25a6dd'::uuid, '1', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('5719950f-180e-4a90-adc7-f9b4c92a4fff'::uuid, '685826d0-d1ff-40ac-8547-56572a135c66'::uuid, '2', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('b7f315e3-72f4-426a-b7f5-4adefb11d8d2'::uuid, 'f9f6d3ea-d37d-4353-9745-e3b484a3a3a7'::uuid, '3', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('40f61488-15ea-44c2-a228-eac5b42dd81a'::uuid, '64c2b7d5-d7a8-428f-b4e7-4df70c447a8d'::uuid, '4', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('12ebc21a-0225-4913-b1cc-f573137844cc'::uuid, '282fbc83-acb8-490f-bcd6-1f748db8a306'::uuid, '5', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');

INSERT INTO public.ledger
(id, transaction_id, account_number, amount, "type")
VALUES('9a05a827-3d88-4dc8-b9d0-06d67042ae6d'::uuid, 'ca4a7fd0-ef74-477c-b002-1f514a934873'::uuid, '6', 9999.00, 'CREDIT', '2025-03-23 10:04:57.581');