package lk.ijse.gdse71.newproject.dao.custom;

import lk.ijse.gdse71.newproject.DTO.FeedbackDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FeedbackDao {
     String getNextFeedbackId() throws SQLException, ClassNotFoundException;
     ArrayList<FeedbackDTO> getAllFeedbacks() throws SQLException, ClassNotFoundException;
     boolean saveFeedback(FeedbackDTO feedbackDTO) throws SQLException, ClassNotFoundException;
     boolean updateFeedback(FeedbackDTO feedbackDTO) throws SQLException, ClassNotFoundException;

    }
