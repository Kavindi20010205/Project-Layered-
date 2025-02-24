package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.ProjectDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProjectDao {
     double getProjectCountByStatus(String status) throws SQLException, ClassNotFoundException;
     ArrayList<String> getAllProjectId() throws SQLException, ClassNotFoundException;
     ProjectDTO findById(String selectedProjectId) throws SQLException, ClassNotFoundException;
     ArrayList<ProjectDTO> getProjectsByStatus(String status) throws SQLException, ClassNotFoundException;
     String getNextProjectId() throws SQLException, ClassNotFoundException;
     ArrayList<String> getStatusNames() throws SQLException, ClassNotFoundException;
     boolean saveProject(ProjectDTO projectDTO) throws SQLException, ClassNotFoundException;
     boolean holdProject(String projectId) throws SQLException, ClassNotFoundException;
     boolean cancelProject(String projectId) throws SQLException, ClassNotFoundException;
     ArrayList<String> getAllProjectIds() throws SQLException, ClassNotFoundException;
     ArrayList<ProjectDTO> getAllProjectDetails() throws SQLException, ClassNotFoundException;
     boolean updateProject(ProjectDTO projectDTO, String updateProjectId) throws SQLException, ClassNotFoundException;
}
