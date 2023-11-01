INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'CREATED') ON CONFLICT DO NOTHING;
INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'VERIFIED') ON CONFLICT DO NOTHING;
INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'REJECTED') ON CONFLICT DO NOTHING;
INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'DELETED') ON CONFLICT DO NOTHING;
INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'PUBLISHED') ON CONFLICT DO NOTHING;
INSERT INTO proposal_manager.PROPOSAL_STATUS (id, TECHNICAL_IDENTIFIER) VALUES (nextval('proposal_manager.proposal_status_id_seq'), 'ACCEPTED') ON CONFLICT DO NOTHING;
