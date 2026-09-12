insert into usuarios (login, senha, perfil)
select 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN'
where not exists (
    select 1 from usuarios where login = 'admin'
);
