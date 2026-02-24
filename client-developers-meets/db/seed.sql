-- Seed data for cd_platform (dev only). This script resets tables.
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE chat_messages;
TRUNCATE chat_participants;
TRUNCATE chat_threads;
TRUNCATE inquiries;
TRUNCATE portfolio_projects;
TRUNCATE developer_profiles;
TRUNCATE client_profiles;
TRUNCATE users;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO users (id, role, email, password_hash) VALUES
  (1, 'CLIENT', 'nimbus@client.com', 'demo_hash'),
  (2, 'DEVELOPER', 'aisha@dev.com', 'demo_hash'),
  (3, 'DEVELOPER', 'lucas@dev.com', 'demo_hash'),
  (4, 'CLIENT', 'orbit@client.com', 'demo_hash');

INSERT INTO developer_profiles (id, user_id, name, title, bio, skills, rate, location, availability) VALUES
  (1, 2, 'Aisha Khan', 'Full-Stack Product Builder',
   'I help founders launch clean, scalable products with thoughtful UX and solid backend foundations.',
   'React,Spring Boot,MySQL,UX', '$55/hr', 'Karachi, PK', 'Available in 2 weeks'),
  (2, 3, 'Lucas Park', 'MVP Architect',
   'Specialized in fast MVPs, lean product strategy, and beautiful interfaces.',
   'Next.js,Node,PostgreSQL,Design Systems', '$65/hr', 'Austin, US', 'Available now');

INSERT INTO portfolio_projects (id, developer_id, title, description, tech_stack, image_url) VALUES
  (1, 1, 'FinSense Budgeting', 'Personal finance app with smart insights and automated reports.',
   'React,Spring Boot,MySQL', ''),
  (2, 1, 'ClinicConnect Portal', 'Patient scheduling and doctor collaboration hub.',
   'Vue,Java,AWS', ''),
  (3, 2, 'ShiftStream Marketplace', 'On-demand staffing platform for hospitality teams.',
   'Next.js,Node,Stripe', '');

INSERT INTO client_profiles (id, user_id, company_name, industry, budget_range) VALUES
  (1, 1, 'Nimbus Health', 'Healthcare', '$20k-$30k'),
  (2, 4, 'Orbit Foods', 'FoodTech', '$15k-$25k');

INSERT INTO inquiries (id, client_id, developer_id, project_summary, status) VALUES
  (1, 1, 1, 'Patient portal MVP', 'New'),
  (2, 2, 2, 'B2B ordering platform', 'In review');

INSERT INTO chat_threads (id) VALUES (1), (2);

INSERT INTO chat_participants (thread_id, user_id) VALUES
  (1, 1), (1, 2),
  (2, 1), (2, 3);

INSERT INTO chat_messages (id, thread_id, sender_id, content) VALUES
  (1, 1, 1, 'Hi Aisha, can we kick off this week?'),
  (2, 1, 2, 'Yes! I can start discovery on Thursday.'),
  (3, 2, 1, 'Hey Lucas, sharing brief today.'),
  (4, 2, 3, 'Great, will review and send timeline.');
