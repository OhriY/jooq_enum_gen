set -e
psql -v ON_ERROR_STOP=1 -U admin testdb <<-EOSQL
  CREATE TABLE test_table(

    test1      VARCHAR(20),
    test2             VARCHAR(100),
    test3   integer
  );
CREATE TYPE test_enum AS ENUM ('enum1', 'enum2', 'enum3');

EOSQL
