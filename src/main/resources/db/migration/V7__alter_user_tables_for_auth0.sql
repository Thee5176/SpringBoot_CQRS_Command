-- This migration alters remove user data and migrate to use managed Auth0 managed service
DROP TABLE credential.users CASCADE;