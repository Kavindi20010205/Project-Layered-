package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.ProjectDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.ProjectDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectDaoImpl implements ProjectDao {
    public double getProjectCountByStatus(String status) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT count(*) FROM Project WHERE Status=?", status);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public ArrayList<String> getAllProjectId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Project_ID FROM Project");

        ArrayList<String> projectIds = new ArrayList<>();

        while (rst.next()) {
            projectIds.add(rst.getString(1));
        }

        return projectIds;
    }

    public ProjectDTO findById(String selectedProjectId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Project WHERE Project_ID=?", selectedProjectId);

        if (rst.next()) {
            return new ProjectDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
        return null;
    }

    public ArrayList<ProjectDTO> getProjectsByStatus(String status) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Project WHERE Status = ?", status);

        ArrayList<ProjectDTO> projectDTOS = new ArrayList<>();

        while (rst.next()) {
            ProjectDTO projectDTO = new ProjectDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }


    public String getNextProjectId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Project_ID FROM Project ORDER BY Project_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String numericPart = lastId.substring(2); // Assumes "PR" is 2 chars
            int nextIdNumber = Integer.parseInt(numericPart) + 1;
            return String.format("PR%03d", nextIdNumber);
        }
        return "PR001";
    }

    public ArrayList<String> getStatusNames() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT DISTINCT Status FROM Project");

        ArrayList<String> statuses = new ArrayList<>();

        while (rst.next()) {
            statuses.add(rst.getString(1));
        }

        return statuses;
    }

    public boolean saveProject(ProjectDTO projectDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "INSERT INTO Project VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                projectDTO.getProjectId(),
                projectDTO.getName(),
                projectDTO.getClientId(),
                projectDTO.getServiceId(),
                projectDTO.getStartDate(),
                projectDTO.getEndDate(),
                projectDTO.getLocation(),
                projectDTO.getAddress(),
                projectDTO.getStatus()
        );
    }

    public boolean holdProject(String projectId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Project SET Status = 'Hold' WHERE Project_ID = ?";
        int rowsUpdated = SQLUtil.execute(query, projectId);
        return rowsUpdated > 0;
    }

    public boolean cancelProject(String projectId) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Project SET Status = 'Cancel' WHERE Project_ID = ?";
        int rowsUpdated = SQLUtil.execute(query, projectId);
        return rowsUpdated > 0;
    }

    public ArrayList<String> getAllProjectIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT Project_ID FROM Project");

        ArrayList<String> projects = new ArrayList<>();

        while (rst.next()) {
            projects.add(rst.getString(1));
        }

        return projects;
    }

    public ArrayList<ProjectDTO> getAllProjectDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Project");

        ArrayList<ProjectDTO> projectDTOS = new ArrayList<>();

        while (rst.next()) {
            ProjectDTO projectDTO = new ProjectDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9)
            );
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }

    public boolean updateProject(ProjectDTO projectDTO, String updateProjectId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "UPDATE Project SET Name=?, Client_ID=?, Service_ID=?, Start_Date=?, End_Date=?, Location=?, Address=?, Status=? WHERE Project_ID=?",
                projectDTO.getName(),
                projectDTO.getClientId(),
                projectDTO.getServiceId(),
                projectDTO.getStartDate(),
                projectDTO.getEndDate(),
                projectDTO.getLocation(),
                projectDTO.getAddress(),
                projectDTO.getStatus(),
                updateProjectId
        );
    }
}
