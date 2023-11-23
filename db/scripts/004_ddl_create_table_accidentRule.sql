CREATE TABLE IF NOT EXISTS accident_rule (
           accident_id INT,
           rule_id INT,
           PRIMARY KEY (accident_id, rule_id),
           FOREIGN KEY (accident_id) REFERENCES accident(id),
          FOREIGN KEY (rule_id) REFERENCES rules(id)
);