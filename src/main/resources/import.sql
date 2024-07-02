-- This data was generated with CHATGPT

-- Inserting into editor
INSERT INTO editor (editor_id, editor_address, editor_email, editor_name) VALUES (NEXTVAL('editor_seq'), '123 Editor St', 'editor1@example.com', 'Editor One');
INSERT INTO editor (editor_id, editor_address, editor_email, editor_name) VALUES (NEXTVAL('editor_seq'), '456 Editor Ave', 'editor2@example.com', 'Editor Two');
INSERT INTO editor (editor_id, editor_address, editor_email, editor_name) VALUES (NEXTVAL('editor_seq'), '789 Editor Blvd', 'editor3@example.com', 'Editor Three');

-- Inserting into author
INSERT INTO author (author_id, firstname, lastname) VALUES (NEXTVAL('author_seq'), 'John', 'Doe');
INSERT INTO author (author_id, firstname, lastname) VALUES (NEXTVAL('author_seq'), 'Jane', 'Smith');
INSERT INTO author (author_id, firstname, lastname) VALUES (NEXTVAL('author_seq'), 'Emily', 'Johnson');
INSERT INTO author (author_id, firstname, lastname) VALUES (NEXTVAL('author_seq'), 'Michael', 'Brown');

-- Inserting into book
INSERT INTO book (book_id, editor_id, isbn, summary, title) VALUES (NEXTVAL('book_seq'), 1, '978-3-16-148410-0', 'A fascinating book', 'Book One');
INSERT INTO book (book_id, editor_id, isbn, summary, title) VALUES (NEXTVAL('book_seq'), 2, '978-1-4028-9462-6', 'Another interesting book', 'Book Two');
INSERT INTO book (book_id, editor_id, isbn, summary, title) VALUES (NEXTVAL('book_seq'), 3, '978-0-596-52068-7', 'Yet another great book', 'Book Three');
INSERT INTO book (book_id, editor_id, isbn, summary, title) VALUES (NEXTVAL('book_seq'), 1, '978-0-201-63361-1', 'An amazing book', 'Book Four');

-- Inserting into author_book
INSERT INTO author_book (author_id, book_id) VALUES (1, 1);
INSERT INTO author_book (author_id, book_id) VALUES (2, 2);
INSERT INTO author_book (author_id, book_id) VALUES (3, 3);
INSERT INTO author_book (author_id, book_id) VALUES (4, 4);
INSERT INTO author_book (author_id, book_id) VALUES (1, 2);
INSERT INTO author_book (author_id, book_id) VALUES (3, 4);

-- Inserting into borrower
INSERT INTO borrower (borrower_id, blocked, block_date, firstname, lastname, phone_number) VALUES (NEXTVAL('borrower_seq'), false, NULL, 'Alice', 'Wonderland', '123-456-7890');
INSERT INTO borrower (borrower_id, blocked, block_date, firstname, lastname, phone_number) VALUES (NEXTVAL('borrower_seq'), true, CURRENT_TIMESTAMP, 'Bob', 'Builder', '098-765-4321');
INSERT INTO borrower (borrower_id, blocked, block_date, firstname, lastname, phone_number) VALUES (NEXTVAL('borrower_seq'), false, NULL, 'Charlie', 'Chaplin', '456-789-0123');
INSERT INTO borrower (borrower_id, blocked, block_date, firstname, lastname, phone_number) VALUES (NEXTVAL('borrower_seq'), false, NULL, 'David', 'Copperfield', '789-012-3456');

-- Inserting into copy
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), true, 1, 'GOOD');
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), false, 2, 'AVERAGE');
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), true, 3, 'GOOD');
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), true, 4, 'DAMAGED');
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), true, 1, 'GOOD'); -- Another copy of Book One
INSERT INTO copy (copy_id, available, book_id, state) VALUES (NEXTVAL('copy_seq'), false, 2, 'DAMAGED'); -- Another copy of Book Two

-- Inserting into borrower_copy
INSERT INTO borrower_copy (borrower_borrower_id, borrow_date, copy_copy_id, actual_return_date, supposed_return_date, copy_state) VALUES (2, CURRENT_TIMESTAMP, 2, NULL, CURRENT_TIMESTAMP + INTERVAL '14 days', 'GOOD');
INSERT INTO borrower_copy (borrower_borrower_id, borrow_date, copy_copy_id, actual_return_date, supposed_return_date, copy_state) VALUES (2, CURRENT_TIMESTAMP - INTERVAL '30 days', 6, CURRENT_TIMESTAMP - INTERVAL '16 days', CURRENT_TIMESTAMP - INTERVAL '16 days', 'DAMAGED');
INSERT INTO borrower_copy (borrower_borrower_id, borrow_date, copy_copy_id, actual_return_date, supposed_return_date, copy_state) VALUES (4, CURRENT_TIMESTAMP - INTERVAL '5 days', 3, NULL, CURRENT_TIMESTAMP + INTERVAL '9 days', 'GOOD');
