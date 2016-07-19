use book_library;
select * from pending left join book
on pending.book_id = book.book_id
where pending.book_id = 1;