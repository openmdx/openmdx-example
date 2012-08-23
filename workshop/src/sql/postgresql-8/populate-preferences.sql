
DELETE FROM prefs_Preference ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 0, 'org:openmdx:preferences1:StringPreference', 'type', NULL, 'PERSISTENCE/type', 'PERSISTENCE', 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 1, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 2, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/project/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 3, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/project/:*/task/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 4, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/project/:*/task/:*/assignment/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 5, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/skill/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 6, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/teamMember/:*'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:type', 7, 'xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/teamMember/:*/task/:*'
) ; 




INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 0, 'org:openmdx:preferences1:StringPreference', 'typeName', NULL, 'PERSISTENCE/typeName', 'PERSISTENCE', 'provider'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 1, 'segment'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 2, 'project'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 3, 'task'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 4, 'assignment'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 5, 'skill'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 6, 'teamMember'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:typeName', 7, 'assignedTask'
) ; 




INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 000, 'org:openmdx:preferences1:StringPreference', 'dbObject', NULL, 'PERSISTENCE/dbObject', 'PERSISTENCE', 'OOM0BASE_PROVIDER'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 000, 'org:openmdx:preferences1:StringPreference', 'dbObject2', NULL, 'PERSISTENCE/dbObject2', 'PERSISTENCE', ''
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 001, 'OOMEP1WS_SEGMENT'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 001, 'OOMEP1WS_SEGMENT_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 002, 'OOMEP1WS_PROJECT'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 002, 'OOMEP1WS_PROJECT_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 003, 'OOMEP1WS_TASK'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 003, 'OOMEP1WS_TASK_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 004, 'OOMEP1WS_TEAMMEMBERASSIGNMENT'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 004, 'OOMEP1WS_TEAMMEMBERASSIGNMENT_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 005, 'OOMEP1WS_SKILL'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 005, 'OOMEP1WS_SKILL_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 006, 'OOMEP1WS_TEAMMEMBER'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject2', 006, 'OOMEP1WS_TEAMMEMBER_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObject', 007, ''
) ; 



INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 000, 'org:openmdx:preferences1:IntegerPreference', 'pathNormalizeLevel', NULL, 'PERSISTENCE/pathNormalizeLevel', 'PERSISTENCE', 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 001, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 002, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 003, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 004, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 005, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 006, 2
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, integer_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:pathNormalizeLevel', 007, 2
) ; 




INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 000, 'org:openmdx:preferences1:StringPreference', 'dbObjectFormat', NULL, 'PERSISTENCE/dbObjectFormat', 'PERSISTENCE', 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 001, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 002, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 003, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 004, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 005, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 006, 'slicedWithIdAsKey'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectFormat', 007, 'slicedWithIdAsKey'
) ; 




INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectForQuery', 000, 'org:openmdx:preferences1:StringPreference', 'dbObjectForQuery', NULL, 'PERSISTENCE/dbObjectForQuery', 'PERSISTENCE', ''
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectForQuery2', 000, 'org:openmdx:preferences1:StringPreference', 'dbObjectForQuery2', NULL, 'PERSISTENCE/dbObjectForQuery2', 'PERSISTENCE', ''
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinTable', 000, 'org:openmdx:preferences1:StringPreference', 'joinTable', NULL, 'PERSISTENCE/joinTable', 'PERSISTENCE', ''
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinColumnEnd1', 000, 'org:openmdx:preferences1:StringPreference', 'joinColumnEnd1', NULL, 'PERSISTENCE/joinColumnEnd1', 'PERSISTENCE', ''
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinColumnEnd2', 000, 'org:openmdx:preferences1:StringPreference', 'joinColumnEnd2', NULL, 'PERSISTENCE/joinColumnEnd2', 'PERSISTENCE', ''
) ; 



INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectForQuery', 007, 'OOMEP1WS_TASK'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:dbObjectForQuery2', 007, 'OOMEP1WS_TASK_'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinTable', 007, 'OOMEP1WS_JOIN_TMEMBERHASTASK'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinColumnEnd1', 007, 'team_member'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:joinColumnEnd2', 007, 'task'
) ; 



INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value)
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:columnNameFrom', 0, 'org:openmdx:preferences1:StringPreference', 'columnNameFrom', NULL, 'PERSISTENCE/columnNameFrom', 'PERSISTENCE', 'object__class'
) ; 


INSERT INTO prefs_Preference 
    (object_rid, object_oid, object_idx, object__class, name, description, absolute_path, parent, string_value) 
VALUES(
    'preference/OOMEP1WS', 'PERSISTENCE:columnNameTo', 0, 'org:openmdx:preferences1:StringPreference', 'columnNameTo', NULL, 'PERSISTENCE/columnNameTo', 'PERSISTENCE', 'dtype'
) ; 

